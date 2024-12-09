package com.dicoding.mybottomnavtest.NewsResponse

import com.google.gson.annotations.SerializedName

data class ArticleViewPagerResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: ArticleViewPagerData? = null
)

data class ArticleViewPagerData(
    @field:SerializedName("article")
    val article: ArticleViewPager? = null
)

data class ArticleViewPager(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("tags")
    val tags: List<String>? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("contents")
    val contents: List<ContentViewPagerItem>? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)

data class ContentViewPagerItem(
    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("type")
    val type: String? = null
)