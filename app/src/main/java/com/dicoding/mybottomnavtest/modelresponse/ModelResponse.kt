package com.dicoding.mybottomnavtest.modelresponse

import com.google.gson.annotations.SerializedName

data class ModelResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("errors")
	val errors: List<ErrorResponse>? = null
)

data class Spice(

	@field:SerializedName("benefits")
	val benefits: String? = null,

	@field:SerializedName("jamuList")
	val jamuList: List<String?>? = null,

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

data class Data(

	@field:SerializedName("probability")
	val probability: String? = null,

	@field:SerializedName("spice")
	val spice: Spice? = null,

	@field:SerializedName("class")
	val jsonMemberClass: String? = null
)

data class ErrorResponse(
	@field:SerializedName("field")
	val field: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
