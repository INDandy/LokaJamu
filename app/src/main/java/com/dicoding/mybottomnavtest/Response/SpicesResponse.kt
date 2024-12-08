package com.dicoding.mybottomnavtest.Response

data class ApiResponse(
    val status: String,
    val data: SpiceData
)

data class SpiceData(
    val spice: Spice
)

data class Spice(
    val id: Int,
    val name: String,
    val tags: List<String>,
    val imageUrl: String,
    val description: String,
    val benefits: List<String>,
    val jamuList: List<String>
)
