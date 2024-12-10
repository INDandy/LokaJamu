package com.dicoding.mybottomnavtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.dicoding.mybottomnavtest.Response.SpicesItem
import com.dicoding.mybottomnavtest.databinding.ItemImageBinding


class SpiceViewPagerAdapter
    (private val spicesList: List<SpicesItem>)
    : RecyclerView.Adapter<SpiceViewPagerAdapter.SpiceViewHolder>() {

    inner class SpiceViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(spiceItem: SpicesItem) {
            Glide.with(binding.root.context)
                .load(spiceItem.imageUrl)
                .override(Target.SIZE_ORIGINAL)
                .fitCenter()
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpiceViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpiceViewPagerAdapter.SpiceViewHolder, position: Int) {
        holder.bind(spicesList[position])
    }

    override fun getItemCount(): Int {
        return spicesList.size
    }
}






