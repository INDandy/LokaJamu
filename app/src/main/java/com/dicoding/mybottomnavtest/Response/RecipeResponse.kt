package com.dicoding.mybottomnavtest.Response

data class RecipeResponse(
    val status: String,
    val data: DataRecipe
)

data class DataRecipe(
    val recipe: Recipe
)

data class Recipe(
    val id: Int,
    val name: String,
    val tags: List<String>,
    val description: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
    val prepTime: String,
    val cookTime: String,
    val totalTime: String,
    val servingSize: String,
    val tips: List<String>
)

data class Ingredient(
    val name: String,
    val quantity: String,
    val notes: String
)

data class Step(
    val stepNumber: Int,
    val instruction: String
)
