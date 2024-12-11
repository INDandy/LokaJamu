package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailSpiceActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.Response.SpicesItem
import com.dicoding.mybottomnavtest.databinding.ItemSpiceRecipeListBinding

class HomeSpiceAdapter(
    private var items: List<SpicesItem> = emptyList()
) : RecyclerView.Adapter<HomeSpiceAdapter.HomeSpiceViewHolder>() {

    class HomeSpiceViewHolder(val binding: ItemSpiceRecipeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSpiceViewHolder {
        val binding = ItemSpiceRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeSpiceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 5 else items.size
    }

    override fun onBindViewHolder(holder: HomeSpiceViewHolder, position: Int) {
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
                    val spiceItem = recipe.id
                    val context = holder.binding.root.context
                    val intent = Intent(context, DetailSpiceActivity::class.java)
                    intent.putExtra("SPICE_ID", spiceItem)
                    context.startActivity(intent)

                }
            }
        } else {
            with(holder.binding) {
                tvRecipeSpice.text = "No recipes available"
                Glide.with(ivRecipeSpice.context)
                    .load(R.drawable.placeholder_image)
                    .into(ivRecipeSpice)

                root.setOnClickListener {
                }
            }
        }
    }

    fun updateData(newItems: List<SpicesItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}




