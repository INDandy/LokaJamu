package com.dicoding.mybottomnavtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.Response.RecipesItem
import com.dicoding.mybottomnavtest.adapter.RecipeAdapter
import com.dicoding.mybottomnavtest.data.RecipeData
import com.dicoding.mybottomnavtest.databinding.ActivityRecipeBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var filteredList: ArrayList<RecipeData>
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRecipes.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(emptyList()) { recipe -> openArticleDetail(recipe) }
        binding.rvRecipes.adapter = recipeAdapter

        newsViewModel.RecipeLiveData.observe(this) { recipes ->
            recipeAdapter = RecipeAdapter(recipes) { recipe -> openArticleDetail(recipe) }
            binding.rvRecipes.adapter = recipeAdapter
            binding.loading.visibility = View.GONE
        }

        binding.rvRecipes.layoutManager = GridLayoutManager(this, 2)
        setSearchView(binding.searchRecipe)

        binding.ivBack.setOnClickListener {
            finish()
        }

       binding.loading.visibility = View.VISIBLE
        newsViewModel.fetchRecipesHome()
    }

    private fun openArticleDetail(recipe: RecipesItem) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra("RECIPE_ID", recipe.id)
        startActivity(intent)
    }


    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnSearchClickListener {
            binding.searchRecipe.queryHint = "Cari Bahan Jamu Disini"
            binding.ivBack.visibility = View.GONE
            binding.tvRecipeHeader.visibility = View.GONE
        }

        searchView.setOnCloseListener {
            binding.ivBack.visibility = View.VISIBLE
            binding.tvRecipeHeader.visibility = View.VISIBLE
            false
        }
    }
}
