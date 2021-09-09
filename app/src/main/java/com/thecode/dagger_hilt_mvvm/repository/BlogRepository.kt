package com.thecode.dagger_hilt_mvvm.repository

import com.thecode.dagger_hilt_mvvm.common.*
import com.thecode.dagger_hilt_mvvm.model.Blog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlin.onFailure

class BlogRepository
constructor(
    private val api: BlogRemoteDataSource,
    private val db: BlogLocalDataSource,
    private val onlineManager: OnlineManager
) {
    private val apiStateFlow = MutableStateFlow<ApiState>(ApiState.Idle)

    suspend fun getBlog(): Flow<DataState<List<Blog>>> {
        return combine(db.getBlogs(), apiStateFlow) { blogs, api ->
            when (api) {
                ApiState.Loading ->
                    DataState.Loading
                is ApiState.Error ->
                    DataState.ErrorWithContent(blogs, api.error)
                ApiState.Idle ->
                    DataState.CompleteWithContent(blogs)
            }
        }
    }

    suspend fun refreshBlogs() {
        onlineManager.performApiCallIfConnected {
            apiStateFlow.emit(ApiState.Loading)
            api.getBlogs()
        }.onSuccess {
            db.insertBlogs(it)
            apiStateFlow.emit(ApiState.Idle)
        }.onFailure {
            apiStateFlow.emit(ApiState.Error(it))
        }
    }
}
