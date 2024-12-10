package com.dicoding.mybottomnavtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.databinding.ItemJamuBinding

class SpiceJamuAdapter(private val jamuList: List<String>) : RecyclerView.Adapter<SpiceJamuAdapter.JamuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JamuViewHolder {
        val binding = ItemJamuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JamuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JamuViewHolder, position: Int) {
        holder.bind(jamuList[position])
    }

    override fun getItemCount(): Int = jamuList.size

    class JamuViewHolder(private val binding: ItemJamuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(jamu: String) {
            binding.tvJamu.text = jamu
        }
    }
}
