package com.dicoding.mybottomnavtest.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.mybottomnavtest.NewsResponse.ContentItem

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    val id: Int,
    val title: String,
    val tags: List<String>?,
    val imageUrl: String?,
    val contents: List<ContentItem>?,
    val createdAt: String?,
    val updatedAt: String?,
    var isFavorite: Boolean = false
)

