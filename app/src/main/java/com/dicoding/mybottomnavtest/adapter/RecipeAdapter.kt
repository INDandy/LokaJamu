package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.Response.RecipesItem

class RecipeAdapter(
    private var articles: List<RecipesItem>,
    private val onItemClick: (RecipesItem) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.iv_recipe_spice)
        private val titleTextView: TextView = view.findViewById(R.id.tv_recipe_spice)

        fun bind(recipe: RecipesItem) {
            Glide.with(itemView.context)
                .load(recipe.imageUrl)
                .into(imageView)

            titleTextView.text = recipe.name


            itemView.setOnClickListener {
                val recipeId = recipe.id
                val context = itemView.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("RECIPE_ID", recipeId)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_spice_recipe_list, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    fun updateArticles(newArticles: Any) {
        articles = newArticles as List<RecipesItem>
        notifyDataSetChanged()
    }
}