package com.thecode.dagger_hilt_mvvm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thecode.dagger_hilt_mvvm.common.db.DateTypeConverter

@Database(
    entities = [
        BlogCacheEntity::class,
        BlogSelectedEntity::class
    ], version = 5
)
@TypeConverters(
    DateTypeConverter::class,
)
abstract class BlogDatabase : RoomDatabase() {
    abstract fun blogDao(): BlogDao
    abstract fun blogSelectedDao(): BlogSelectedDao

    companion object {
        const val DATABASE_NAME: String = "blog_db"
    }
}
