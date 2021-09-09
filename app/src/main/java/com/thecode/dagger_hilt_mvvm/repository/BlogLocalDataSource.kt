package com.thecode.dagger_hilt_mvvm.repository

import com.thecode.dagger_hilt_mvvm.database.BlogDao
import com.thecode.dagger_hilt_mvvm.database.BlogCacheMapper
import com.thecode.dagger_hilt_mvvm.database.BlogSelectedDao
import com.thecode.dagger_hilt_mvvm.database.BlogSelectedMapper
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.model.BlogSelected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BlogLocalDataSource @Inject constructor(
    private val blogDao: BlogDao,
    private val blogSelectedDao: BlogSelectedDao,
    private val blogCacheMapper: BlogCacheMapper,
    private val blogSelectedMapper: BlogSelectedMapper
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

    suspend fun getBlogsSelected(): Flow<List<BlogSelected>> {
        return blogSelectedDao.get()
            .distinctUntilChanged()
            .map {
                blogSelectedMapper.mapFromEntityList(it)
            }
    }

    suspend fun insertBlogSelected(blogSelected: BlogSelected) {
        blogSelectedDao.insert(blogSelectedMapper.mapToEntity(blogSelected))
    }
}
