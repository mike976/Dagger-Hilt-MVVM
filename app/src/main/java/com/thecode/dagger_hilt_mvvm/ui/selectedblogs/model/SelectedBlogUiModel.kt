package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model

import java.util.Date

data class SelectedBlogUiModel(
    val id: Long,
    val title: String,
    val date: Date?,
    val body: String,
    val image: String,
    val isSelected: Boolean = false
)
