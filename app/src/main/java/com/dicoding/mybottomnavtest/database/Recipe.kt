package com.dicoding.mybottomnavtest.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.mybottomnavtest.Response.IngredientsItem
import com.dicoding.mybottomnavtest.Response.StepsItem

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    val id: Int? = null,
    val totalTime: String? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val cookTime: String? = null,
    val description: String? = null,
    val ingredients: List<IngredientsItem?>? = null,
    val steps: List<StepsItem?>? = null,
    val servingSize: String? = null,
    val tips: List<String?>? = null,
    val tags: List<String?>? = null,
    val prepTime: String? = null,
    var isFavorite: Boolean = false

)