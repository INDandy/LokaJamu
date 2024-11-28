package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@SerializedName("status") val status: String?,
	@SerializedName("message") val message: String?
)

