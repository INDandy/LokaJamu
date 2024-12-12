package com.dicoding.mybottomnavtest.Response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

	@field:SerializedName("data")
	val data: DataRecipes? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataRecipes(

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem?>? = null,

	@field:SerializedName("recipe")
	val recipe: RecipesItem? = null
)

data class StepsItem(

	@field:SerializedName("instruction")
	val instruction: String? = null,

	@field:SerializedName("stepNumber")
	val stepNumber: String? = null
)

data class IngredientsItem(

	@field:SerializedName("ingredient")
	val ingredient: String? = null,

	@field:SerializedName("quantity")
	val quantity: String? = null,

	@field:SerializedName("notes")
	val notes: String? = null
)

data class RecipesItem(

	@field:SerializedName("totalTime")
	val totalTime: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("cookTime")
	val cookTime: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("steps")
	val steps: List<StepsItem?>? = null,

	@field:SerializedName("servingSize")
	val servingSize: String? = null,

	@field:SerializedName("tips")
	val tips: List<String?>? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	@field:SerializedName("prepTime")
	val prepTime: String? = null
)
