package com.thecode.dagger_hilt_mvvm

import com.thecode.dagger_hilt_mvvm.model.Blog

fun emptyBlogModel() = Blog(
    id = 0,
    title = "",
    body = "",
    image = "",
    category = ""
)
