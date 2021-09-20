package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens.previews

import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model.SelectedBlogUiModel

fun emptySelectedBlogUiModel() = SelectedBlogUiModel(
    id = 0,
    title = "",
    body = "",
    image = "",
    isSelected = false,
    date = null
)
