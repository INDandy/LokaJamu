package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailRecipeActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.Response.RecipesItem
import com.dicoding.mybottomnavtest.databinding.ItemSpiceRecipeListBinding

class HomeRecipeAdapter(
    private var items: List<RecipesItem> = emptyList()
) : RecyclerView.Adapter<HomeRecipeAdapter.HomeRecipeViewHolder>() {

    class HomeRecipeViewHolder(val binding: ItemSpiceRecipeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecipeViewHolder {
        val binding = ItemSpiceRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeRecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 5 else items.size
    }

    override fun onBindViewHolder(holder: HomeRecipeViewHolder, position: Int) {
        if (items.isNotEmpty() && position < items.size) {
            val recipe = items[position]
            with(holder.binding) {
                tvRecipeSpice.text = recipe.name ?: "No Title"

                Glide.with(ivRecipeSpice.context)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.no_content)
                    .error(R.drawable.error_image)
                    .into(ivRecipeSpice)

                root.setOnClickListener {
                    val recipeId = recipe.id
                    val context = holder.binding.root.context
                    val intent = Intent(context, DetailRecipeActivity::class.java)
                    intent.putExtra("RECIPE_ID", recipeId)
                    context.startActivity(intent)
                }
            }
        } else {
            with(holder.binding) {
                tvRecipeSpice.text = "Error Recipes"
                Glide.with(ivRecipeSpice.context)
                    .load(R.drawable.lokajamulogo)
                    .placeholder(R.drawable.lokajamulogo)
                    .error(R.drawable.error_image)
                    .into(ivRecipeSpice)

                root.setOnClickListener {
                }
            }
        }
    }

    fun updateData(newItems: List<RecipesItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}




