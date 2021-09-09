package com.thecode.dagger_hilt_mvvm.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "blogs_selected")
data class BlogSelectedEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "date")
    var date: Date?,
)
