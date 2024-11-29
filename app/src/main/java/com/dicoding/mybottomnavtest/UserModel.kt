package com.dicoding.mybottomnavtest

data class UserModel(
    var email: String,
    var password: String,
    val token: String,
    val isLoggedIn: Boolean = token.isNotEmpty()
)