package com.thecode.dagger_hilt_mvvm

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKeyAlias: String by lazy { MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC) }

    companion object {
        // private const val PREFS_FILENAME = "com.thecode.dagger.hilt.mvvm.app.prefs"
        private const val PREFS_FILENAME_SECURE = "com.thecode.dagger.hilt.mvvm.app.prefs.secure"
        private const val PREF_KEY_SERVER_URL_KEY = "serverUrl"
        private const val PREF_KEY_SERVER_URL_VALUE = "https://open-api.xyz/placeholder/"
    }

    @Suppress("ForbiddenComment")
    private val prefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            PREFS_FILENAME_SECURE,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
//            // TODO: Remove in a future release
//            // Migrate old prefs to secure prefs
//            .also { oldPrefs ->
//                with(context.getSharedPreferences(PREFS_FILENAME, 0)) {
//                    if (this.all.isNotEmpty()) {
//                        this.copyTo(oldPrefs)
//                        this.edit().clear().apply()
//                    }
//                }
//            }
    }

    var serverUrl: String
        get() = prefs.getString(PREF_KEY_SERVER_URL_KEY, PREF_KEY_SERVER_URL_VALUE)!!
        set(value) = prefs.edit().putString(PREF_KEY_SERVER_URL_KEY, value).apply()
}
