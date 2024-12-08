package com.dicoding.mybottomnavtest.bookmarkResponse

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(
    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: BookmarkData? = null
)

data class BookmarkData(
    @field:SerializedName("id")
    val userId: Int? = null,

    @field:SerializedName("bookmarkedArticles")
    val bookmarkedArticles: List<BookmarkedArticle>? = null
)

data class BookmarkedArticle(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null
)

