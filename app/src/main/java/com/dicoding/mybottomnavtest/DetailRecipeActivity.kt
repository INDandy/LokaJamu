package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.FragmentDetail.BottomSheetDetailRecipe
import com.dicoding.mybottomnavtest.Response.RecipesItem
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.databinding.ActivityDetailRecipeBinding
import kotlinx.coroutines.launch

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId == -1) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fetchRecipeDetails(recipeId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchRecipeDetails(id: Int) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.RecipeApiService().getRecipeById(id)
                if (response.isSuccessful) {
                    val recipe = response.body()?.data?.recipe
                    if (recipe != null) {
                        displayRecipeDetails(recipe)
                    } else {
                        Log.e("API Error", "Recipe not found for ID: $id")
                        Toast.makeText(this@DetailRecipeActivity, "Recipe not found", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Log.e("API Error", "Failed to fetch data: ${response.errorBody()?.string()}")
                    Toast.makeText(this@DetailRecipeActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Log.e("API Exception", "Error: ${e.message}", e)
                Toast.makeText(this@DetailRecipeActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun displayRecipeDetails(recipe: RecipesItem) {
        Glide.with(this)
            .load(recipe.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.ivRecipeSpiceDetail)

        val bundle = Bundle().apply {
            putString("name", recipe.name)
            putString("description", recipe.description)

            val ingredients = recipe.ingredients?.joinToString(separator = "\n") {
                "${it?.quantity ?: ""} ${it?.ingredient ?: ""} (${it?.notes ?: ""})"
            }
            putString("ingredients", ingredients)

            val steps = recipe.steps?.joinToString(separator = "\n") {
                "Step ${it?.stepNumber ?: ""}: ${it?.instruction ?: ""}"
            }
            putString("steps", steps)

            val tips = recipe.tips?.joinToString(separator = "\n")
            putString("tips", tips)

            putString("tags", recipe.tags?.joinToString(", ") ?: "No Tags")
        }

        val bottomSheet = BottomSheetDetailRecipe(bundle)
        bottomSheet.show(supportFragmentManager, BottomSheetDetailRecipe::class.java.simpleName)
    }
}