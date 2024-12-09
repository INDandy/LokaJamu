package com.dicoding.mybottomnavtest.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.DetailRecipeActivity
import com.dicoding.mybottomnavtest.data.RecipeData
import com.dicoding.mybottomnavtest.databinding.ItemSpiceRecipeListBinding

class RecipeAdapter(val context: Context?, private val items: List<RecipeData>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSpiceRecipeListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpiceRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.tvRecipeSpice.text = this.name
                binding.ivRecipeSpice.setImageResource(this.image)

                binding.root.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("name", this.name)
                    bundle.putInt("image", this.image)
                    bundle.putString("description", this.description)
                    bundle.putString("ingredients", this.description)
                    bundle.putString("steps", this.description)
                    bundle.putString("tips", this.description)

                    val intent = Intent(context, DetailRecipeActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            }
        }
    }
}