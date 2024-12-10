package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class SpicesResponse(

	@field:SerializedName("data")
	val data: DataSpice? = null,

	@field:SerializedName("status")
	val status: String? = null
)



data class SpicesItem(

	@field:SerializedName("benefits")
	val benefits: String? = null,

	@field:SerializedName("jamuList")
	val jamuList: List<Any?>? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null
)

data class DataSpice(

	@field:SerializedName("spices")
	val spices: List<SpicesItem?>? = null,

	@field:SerializedName("spice")
	val spice: SpicesItem? = null
)
