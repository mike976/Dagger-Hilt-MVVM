package com.thecode.dagger_hilt_mvvm.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.annotation.VisibleForTesting
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    // region Public
    fun hasInternetConnectionFlow(): Flow<Boolean> =
        combine(_mutableConnectionFlow, _mutableNetworkFlow) {
                connection, network -> connection && network
        }.distinctUntilChanged()

    fun checkInternetConnection(): Boolean {
        return _mutableConnectionFlow.value
    }

    suspend fun <T> performApiCallIfConnected(apiCall: suspend () -> Result<T>): Result<T> {
        val hasConnection = _mutableConnectionFlow.value
        return if (hasConnection.not()) {
            Result.Failure(NetworkException("No connection"))
        } else {
            apiCall().also {
                postNetworkState(it)
                // reportApiException(it)
            }
        }
    }
    // endregion

    // region Private
    // has connection to wifi/mobile/ethernet...
    private val _mutableConnectionFlow = MutableStateFlow(true)

    // Can connect with the API
    private val _mutableNetworkFlow = MutableStateFlow(true)

    @VisibleForTesting
    fun postHasConnection(hasConnection: Boolean) {
        _mutableConnectionFlow.value = hasConnection
    }

    private fun <T> postNetworkState(result: Result<T>) = result
        .onSuccess { _mutableNetworkFlow.value = true }
        .onFailure { _mutableNetworkFlow.value = it.isNetworkException().not() }

//    private fun <T> reportApiException(result: Result<T>) = result.onFailure {
//        // if exception logging is too frequent may be worth only logging non-network errors
//        // if (it.isNetworkException().not())
//        reporting.logException(it)
//    }

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                postHasConnection(false)
            }

            override fun onAvailable(network: Network) {
                postHasConnection(true)
            }
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnection()
        }
    }

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            context.registerReceiver(
                networkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    // Not required as this Singleton lives the entire life of the application
    private fun cleanUp() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    @VisibleForTesting
    fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postHasConnection(activeNetwork?.isConnected == true)
    }

    // endregion
}
