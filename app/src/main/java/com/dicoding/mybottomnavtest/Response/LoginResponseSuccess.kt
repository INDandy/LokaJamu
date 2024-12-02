package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class LoginResponseSuccess(
	@SerializedName("data") val data: Data? = null,

	@SerializedName("status") val status: String? = null
)

data class Data(
	@SerializedName("token") val token: String? = null
)
