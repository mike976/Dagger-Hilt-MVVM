package com.thecode.dagger_hilt_mvvm.repository

import com.thecode.dagger_hilt_mvvm.database.BlogDao
import com.thecode.dagger_hilt_mvvm.database.CacheMapper
import com.thecode.dagger_hilt_mvvm.model.Blog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BlogLocalDataSource @Inject constructor(
    private val blogDao: BlogDao,
    private val cacheMapper: CacheMapper
) {

    suspend fun getBlogs(): Flow<List<Blog>> {
        return blogDao.get()
            .distinctUntilChanged()
            .map {
                cacheMapper.mapFromEntityList(it)
            }
    }

    suspend fun insertBlogs(blogs: List<Blog>) {
        for (blog in blogs) {
            blogDao.insert(cacheMapper.mapToEntity(blog))
        }
    }
}
