package com.dicoding.mybottomnavtest.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.DetailSpiceActivity
import com.dicoding.mybottomnavtest.data.SpiceData
import com.dicoding.mybottomnavtest.databinding.ItemSpiceRecipeListBinding

class SpiceAdapter(val context: Context?, private val items: List<SpiceData>) :
    RecyclerView.Adapter<SpiceAdapter.ViewHolder>() {

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
                    bundle.putString("benefits", this.benefit)
                    bundle.putString("jamulist", this.benefit)

                    val intent = Intent(context, DetailSpiceActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            }
        }
    }
}