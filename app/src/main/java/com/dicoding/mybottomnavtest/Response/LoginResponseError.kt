package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class LoginResponseError(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Errors? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Errors(

	@field:SerializedName("field")
	val field: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
