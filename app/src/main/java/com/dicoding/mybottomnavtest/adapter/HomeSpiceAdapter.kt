package com.dicoding.mybottomnavtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.Response.SpicesItem
import com.dicoding.mybottomnavtest.databinding.ItemHomeHorizontalBinding

class HomeSpiceAdapter(
    private var items: List<SpicesItem> = emptyList()
) : RecyclerView.Adapter<HomeSpiceAdapter.HomeSpiceViewHolder>() {

    class HomeSpiceViewHolder(val binding: ItemHomeHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSpiceViewHolder {
        val binding = ItemHomeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeSpiceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 5 else items.size
    }

    override fun onBindViewHolder(holder: HomeSpiceViewHolder, position: Int) {
        if (items.isNotEmpty() && position < items.size) {
            val recipe = items[position]
            with(holder.binding) {
                tvHomeHorizontal.text = recipe.name ?: "No Title"

                Glide.with(ivHomeHorizontal.context)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.no_content)
                    .error(R.drawable.error_image)
                    .into(ivHomeHorizontal)

                root.setOnClickListener {

                }
            }
        } else {
            with(holder.binding) {
                tvHomeHorizontal.text = "No recipes available"
                Glide.with(ivHomeHorizontal.context)
                    .load(R.drawable.placeholder_image)
                    .into(ivHomeHorizontal)

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




