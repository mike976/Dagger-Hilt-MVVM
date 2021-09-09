package com.thecode.dagger_hilt_mvvm.repository

import com.thecode.dagger_hilt_mvvm.database.BlogDao
import com.thecode.dagger_hilt_mvvm.database.BlogCacheMapper
import com.thecode.dagger_hilt_mvvm.model.Blog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BlogLocalDataSource @Inject constructor(
    private val blogDao: BlogDao,
    private val blogCacheMapper: BlogCacheMapper
) {

    suspend fun getBlogs(): Flow<List<Blog>> {
        return blogDao.get()
            .distinctUntilChanged()
            .map {
                blogCacheMapper.mapFromEntityList(it)
            }
    }

    suspend fun insertBlogs(blogs: List<Blog>) {
        for (blog in blogs) {
            blogDao.insert(blogCacheMapper.mapToEntity(blog))
        }
    }
}
