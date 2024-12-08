package com.dicoding.mybottomnavtest.bookmarkResponse

data class GenericResponse<T>(
    val status: Boolean,
    val message: String,
    val data: Any? = null
)

data class BookmarkArticle(
    val id: Int,
    val title: String,
    val author: String,
    val imageUrl: String,
    val createdAt: String
)
