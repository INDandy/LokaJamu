package com.dicoding.mybottomnavtest.NewsResponse

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ArticlesItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("contents")
	val contents: List<ContentsItem?>? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Data(

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null
)

data class ContentsItem(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)


