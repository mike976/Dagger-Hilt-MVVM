package com.thecode.dagger_hilt_mvvm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogSelectedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogSelectedEntity: BlogSelectedEntity): Long

    @Query("SELECT * FROM blogs_selected")
    fun get(): Flow<List<BlogSelectedEntity>>
}
