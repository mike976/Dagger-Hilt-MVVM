package com.thecode.dagger_hilt_mvvm.di

import com.thecode.dagger_hilt_mvvm.AppDispatchers
import com.thecode.dagger_hilt_mvvm.AppDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindDispatchers(impl: AppDispatchersImpl): AppDispatchers

    companion object {
        @Singleton
        @Provides
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    }
}
