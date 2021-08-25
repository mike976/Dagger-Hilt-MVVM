package com.thecode.dagger_hilt_mvvm.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thecode.dagger_hilt_mvvm.AppSharedPreferences
import com.thecode.dagger_hilt_mvvm.network.BlogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 80L

    @Singleton
    @Provides
    fun provideOkhttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        // if (BuildConfig.DEBUG) builder
            // .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        prefs: AppSharedPreferences
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(prefs.serverUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit): BlogApi {
        return retrofit
            .create(BlogApi::class.java)
    }
}
