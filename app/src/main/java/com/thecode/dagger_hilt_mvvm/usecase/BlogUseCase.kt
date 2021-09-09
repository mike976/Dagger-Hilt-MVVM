package com.thecode.dagger_hilt_mvvm.usecase

import com.thecode.dagger_hilt_mvvm.common.DataState
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlogUseCase @Inject constructor(
    private val blogRepository: BlogRepository
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = blogRepository.getBlog()

    suspend fun refreshBlogs() = blogRepository.refreshBlogs()
}
