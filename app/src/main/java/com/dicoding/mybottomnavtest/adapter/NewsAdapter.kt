package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.finalsubmission1.data.ListEventsItem
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.FragmentEvents.NewsFragment
import com.dicoding.mybottomnavtest.databinding.ItemNewsBinding

class NewsAdapter(private val events: List<ListEventsItem>, private val fragment: NewsFragment) :
    RecyclerView.Adapter<NewsAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
            binding.newsName.text = event.name
            Glide.with(fragment)
                .load(event.imageLogo)
                .into(binding.newsImage)

            binding.root.setOnClickListener {
                val eventId = event.id
                val intent = Intent(fragment.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                fragment.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }
}