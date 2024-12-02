package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class DataUser(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class GetUsers(

	@field:SerializedName("data")
	val data: DataUser? = null,

	@field:SerializedName("status")
	val status: String? = null
)
