package com.thecode.dagger_hilt_mvvm.common

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Are we able to receive data from network
 */
fun Throwable.isNetworkException(): Boolean = when (this) {
    is UnknownHostException,
    is SocketTimeoutException,
    is IOException,
        // no HttpException as this indicates there is network
    is ConnectException -> true
    else -> false
}
