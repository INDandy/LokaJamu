package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.FragmentEvents.HomeFragment
import com.dicoding.mybottomnavtest.data.ListEventsItem
import com.dicoding.mybottomnavtest.databinding.ItemHorizontalBinding
import com.dicoding.mybottomnavtest.databinding.ItemImageBinding

class HomeAdapter(private var events: List<ListEventsItem>, private val fragment: HomeFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_IMAGE = 0
    private val TYPE_HORIZONTAL = 1

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(fragment)
                .load(event.imageLogo)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val eventId = event.id
                val intent = Intent(fragment.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                fragment.startActivity(intent)
            }
        }
    }

    inner class RecipesViewHolder(private val binding: ItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(fragment)
                .load(event.imageLogo)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val eventId = event.id
                val intent = Intent(fragment.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                fragment.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) TYPE_IMAGE else TYPE_HORIZONTAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> {
                val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageViewHolder(binding)
            }
            TYPE_HORIZONTAL -> {
                val binding = ItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RecipesViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = events[position]
        when (holder) {
            is ImageViewHolder -> holder.bind(event)
            is RecipesViewHolder -> holder.bind(event)
        }
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<ListEventsItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}

