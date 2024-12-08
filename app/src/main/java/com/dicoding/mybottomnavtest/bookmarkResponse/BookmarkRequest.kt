package com.dicoding.mybottomnavtest.bookmarkResponse

import com.google.gson.annotations.SerializedName

data class BookmarkRequest(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("articleId")
    val articleId: Int?
)
