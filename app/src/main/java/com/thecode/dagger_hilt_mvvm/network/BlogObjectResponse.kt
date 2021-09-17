package com.thecode.dagger_hilt_mvvm.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogObjectResponse(
    @SerializedName("pk")
    @Expose
    val id: Long,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("body")
    @Expose
    val body: String,

    @SerializedName("category")
    @Expose
    val category: String,

    @SerializedName("image")
    @Expose
    val image: String
)
