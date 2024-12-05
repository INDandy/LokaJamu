package com.dicoding.mybottomnavtest.data

data class SpiceData(
    val id: Int,
    val name: String,
    val image: Int,
    val description: String,
    val benefit: String,
    val isFavorite: Boolean = false
)
