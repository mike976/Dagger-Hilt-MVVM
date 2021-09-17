package com.thecode.dagger_hilt_mvvm.model

import java.util.Date

data class BlogSelected(
    val id: Long,
    val title: String,
    val date: Date?,
    val body: String,
    val image: String
)
