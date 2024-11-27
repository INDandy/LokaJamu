package com.dicoding.mybottomnavtest.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.data.ArticleData
import com.dicoding.mybottomnavtest.databinding.ItemLatestNewsBinding
import com.dicoding.mybottomnavtest.databinding.ItemNewsBinding

class NewsAdapter(private val items: List<ArticleData>) :
RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

//    inner class EventViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(event: ListEventsItem) {
//            binding.newsName.text = event.name
//            binding.newsDate.text = event.beginTime
//
//            Glide.with(fragment)
//                .load(event.imageLogo)
//                .into(binding.newsImage)
//
//            binding.root.setOnClickListener {
//                val eventId = event.id
//                val intent = Intent(fragment.context, DetailActivity::class.java)
//                intent.putExtra("EVENT_ID", eventId)
//                fragment.startActivity(intent)
//            }
//        }
//    }
    class ViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.tvArticleTitle.text = this.title
                binding.tvArticleDate.text = this.date
                binding.ivArticle.setImageResource(this.image)
            }
        }
    }
}