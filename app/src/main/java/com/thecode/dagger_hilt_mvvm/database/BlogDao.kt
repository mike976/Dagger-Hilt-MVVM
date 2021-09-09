package com.thecode.dagger_hilt_mvvm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogEntity: BlogCacheEntity): Long

    @Query("SELECT * FROM blogs")
    fun get(): Flow<List<BlogCacheEntity>>
}
