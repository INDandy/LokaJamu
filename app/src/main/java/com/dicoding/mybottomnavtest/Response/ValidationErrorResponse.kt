package com.dicoding.mybottomnavtest.Response

data class ValidationErrorResponse(
    val status: String,
    val message: String,
    val errors: List<ValidationError>
)

data class ValidationError(
    val field: String,
    val message: String
)