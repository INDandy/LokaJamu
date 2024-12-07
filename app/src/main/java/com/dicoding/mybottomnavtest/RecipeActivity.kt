package com.dicoding.mybottomnavtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.mybottomnavtest.adapter.RecipeAdapter
import com.dicoding.mybottomnavtest.data.RecipeData
import com.dicoding.mybottomnavtest.databinding.ActivityRecipeBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var filteredList: ArrayList<RecipeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRecipes.layoutManager = GridLayoutManager(this, 2)
        setRecipeAdapter()
        setSearchView(binding.searchRecipe)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setRecipeAdapter() {
        val dataList: MutableList<RecipeData> = mutableListOf()

        recipeNameDummy().forEachIndexed { index, name ->
            dataList.add(
                RecipeData(
                    index,
                    name,
                    ImageDummy()[index],
                    recipeDescriptionDummy()[index],
                    recipeBenefitDummy()[index]
                )
            )
        }

        recipeAdapter = RecipeAdapter(this, dataList)
        binding.rvRecipes.adapter = recipeAdapter
    }

    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnSearchClickListener {
            binding.searchRecipe.queryHint = "Cari Resep Disini"
            binding.ivBack.visibility = View.GONE
            binding.tvRecipeHeader.visibility = View.GONE
        }

        searchView.setOnCloseListener {
            binding.ivBack.visibility = View.VISIBLE
            binding.tvRecipeHeader.visibility = View.VISIBLE
            false
        }
    }

    private fun setAdapterOnSearch(data: MutableList<RecipeData>) {
        filteredList = ArrayList(data)

        binding.searchRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                data.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())

                if (searchText.isNotEmpty()) {
                    filteredList.forEach {
                        if (it.name.lowercase(Locale.getDefault()).contains(searchText)) {
                            data.add(it)
                        }
                    }
                    recipeAdapter.notifyDataSetChanged()

                    if(data.isEmpty()) {
                        Snackbar.make(binding.root, "Resep Tidak Ditemukan", Snackbar.LENGTH_SHORT)
                            .setAction("Hapus") { binding.searchRecipe.setQuery("", false) }
                            .setActionTextColor(ContextCompat.getColor(binding.root.context, R.color.oranye))
                            .show()
                    }
                }
                else {
                    data.clear()
                    data.addAll(filteredList)
                    recipeAdapter.notifyDataSetChanged()
                }
                return false
            }
        })
    }

    private fun recipeNameDummy(): Array<String> {
        return resources.getStringArray(R.array.RecipeName)
    }
    private fun ImageDummy(): List<Int> {
        return listOf(
            R.drawable.image_article_1,
            R.drawable.image_article_2,
            R.drawable.image_article_3,
            R.drawable.image_article_3,
            R.drawable.image_article_3
        )
    }
    private fun recipeDescriptionDummy(): Array<String> {
        return resources.getStringArray(R.array.RecipeDescription)
    }
    private fun recipeBenefitDummy(): Array<String> {
        return resources.getStringArray(R.array.RecipeBenefit)
    }
}