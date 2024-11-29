package com.dicoding.mybottomnavtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.data.ArticleData
import com.dicoding.mybottomnavtest.databinding.ItemNewsBinding

class NewsAdapter(private val items: List<ArticleData>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

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