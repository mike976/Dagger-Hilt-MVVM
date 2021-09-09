package com.thecode.dagger_hilt_mvvm.repository

import com.thecode.dagger_hilt_mvvm.common.Result
import com.thecode.dagger_hilt_mvvm.common.runCatching
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.network.BlogApi
import com.thecode.dagger_hilt_mvvm.network.BlogMapper
import javax.inject.Inject

class BlogRemoteDataSource @Inject constructor(
    private val blogApi: BlogApi,
    private val blogMapper: BlogMapper
) {

    suspend fun getBlogs(): Result<List<Blog>> = runCatching {
        blogMapper.mapFromEntityList(blogApi.get())
    }
}
