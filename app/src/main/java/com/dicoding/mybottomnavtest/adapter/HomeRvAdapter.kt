package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.FragmentEvents.HomeFragment
import com.dicoding.mybottomnavtest.data.ListEventsItem
import com.dicoding.mybottomnavtest.databinding.ItemRvHorizontalBinding

class HomeRvAdapter(private var events: List<ListEventsItem>, private val fragment: HomeFragment) :
    RecyclerView.Adapter<HomeRvAdapter.HorizontalViewHolder>() {

    inner class HorizontalViewHolder(private val binding: ItemRvHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
            binding.newsName.text = event.name

            Glide.with(fragment)
                .load(event.imageLogo)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val eventId = event.id
                val intent = Intent(fragment.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                fragment.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val binding = ItemRvHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(events[position]) // Bind the event data to the view
    }

    override fun getItemCount(): Int {
        return events.size
    }

    // Update the events list in the adapter
    fun updateData(newEvents: List<ListEventsItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
