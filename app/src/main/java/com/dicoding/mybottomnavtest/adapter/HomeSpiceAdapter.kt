package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.Response.Spice
import com.dicoding.mybottomnavtest.databinding.ItemSpiceRecipeListBinding

class HomeSpiceAdapter(
    private var items: List<Spice> = emptyList()
) : RecyclerView.Adapter<HomeSpiceAdapter.HomeSpiceViewHolder>() {

    class HomeSpiceViewHolder(val binding: ItemSpiceRecipeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSpiceViewHolder {
        val binding = ItemSpiceRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeSpiceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 5 else items.size // Default 5 items for dummy
    }

    override fun onBindViewHolder(holder: HomeSpiceViewHolder, position: Int) {
        if (items.isNotEmpty() && position < items.size) {
            val spice = items[position]
            with(holder.binding) {
                tvRecipeSpice.text = spice.name

                Glide.with(ivRecipeSpice.context)
                    .load(spice.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(ivRecipeSpice)

                root.setOnClickListener {
                    val spiceId = spice.id
                    val context = root.context
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("EVENT_ID", spiceId)
                    context.startActivity(intent)
                }
            }
        } else {
            // Dummy data
            with(holder.binding) {
                tvRecipeSpice.text = "Dummy Spice"

                Glide.with(ivRecipeSpice.context)
                    .load(R.drawable.placeholder_image)  // Replace with your dummy image resource
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(ivRecipeSpice)

                root.setOnClickListener {
                    // Optionally, do nothing or show a toast for dummy items
                }
            }
        }
    }

    fun updateData(newItems: List<Spice>) {
        items = if (newItems.isEmpty()) {
            // Generate dummy items with default values
            List(5) {
                Spice(
                    id = -1,
                    name = "Dummy Spice ${it + 1}",
                    tags = listOf("dummy", "placeholder"),
                    imageUrl = "",
                    description = "This is a dummy spice description.",
                    benefits = listOf("Dummy benefit 1", "Dummy benefit 2"),
                    jamuList = listOf("Dummy Jamu 1", "Dummy Jamu 2")
                )
            }
        } else {
            newItems
        }
        notifyDataSetChanged()
    }
}
