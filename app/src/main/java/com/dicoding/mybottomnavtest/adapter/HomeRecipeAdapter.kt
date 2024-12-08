package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.Response.Recipe
import com.dicoding.mybottomnavtest.databinding.ItemSpiceRecipeListBinding

class HomeRecipeAdapter(
    private var items: List<Recipe>
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
        val recipe = items[position]
        with(holder.binding) {
            tvRecipeSpice.text = recipe.name

            Glide.with(ivRecipeSpice.context)
                .load(recipe.imageUrl)
                .into(ivRecipeSpice)

            root.setOnClickListener {
                val recipeId = recipe.id
                val context = root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", recipeId)
                context.startActivity(intent)
            }

        }
    }

}