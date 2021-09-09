package com.thecode.dagger_hilt_mvvm.di

import com.thecode.dagger_hilt_mvvm.common.OnlineManager
import com.thecode.dagger_hilt_mvvm.repository.BlogLocalDataSource
import com.thecode.dagger_hilt_mvvm.repository.BlogRemoteDataSource
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import com.thecode.dagger_hilt_mvvm.workmanager.BlogSelectedScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogApi: BlogRemoteDataSource,
        blogDb: BlogLocalDataSource,
        onlineManager: OnlineManager,
        blogSelectedScheduler: BlogSelectedScheduler
    ): BlogRepository {
        return BlogRepository(blogApi, blogDb, onlineManager, blogSelectedScheduler)
    }
}
