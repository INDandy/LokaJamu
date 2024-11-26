package com.dicoding.mybottomnavtest.data

data class ArticleData(
    val image: Int,
    val title: String,
    val date: String,
    val author: String,
    val content: String,
    val isFavorite: Boolean = false
)
