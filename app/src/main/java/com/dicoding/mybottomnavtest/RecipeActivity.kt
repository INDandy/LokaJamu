package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.mybottomnavtest.adapter.HomeRecipeAdapter
import com.dicoding.mybottomnavtest.databinding.ActivityRecipeBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var homeRecipeAdapter: HomeRecipeAdapter
    private lateinit var progressBar: ProgressBar

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecipes()

        binding.loading.visibility = View.VISIBLE

        newsViewModel.fetchRecipesHome()
    }

    private fun setupRecipes() {
        progressBar = binding.loading
        binding.rvRecipes.layoutManager = GridLayoutManager(this, 2)
        homeRecipeAdapter = HomeRecipeAdapter(emptyList())

        binding.rvRecipes.adapter = homeRecipeAdapter

        newsViewModel.RecipeLiveData.observe(this) { homeRecipe ->
            if (homeRecipe.isNotEmpty()) {
                homeRecipeAdapter.updateData(homeRecipe)
            } else {
                homeRecipeAdapter.updateData(emptyList())
            }
            binding.loading.visibility = View.GONE
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        setSearchView(binding.searchRecipe)
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
