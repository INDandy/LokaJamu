package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class RegisterResponseError(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
