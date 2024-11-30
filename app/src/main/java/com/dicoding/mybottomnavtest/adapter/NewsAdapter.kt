package com.dicoding.mybottomnavtest.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.DetailArticleActivity
import com.dicoding.mybottomnavtest.data.ArticleData
import com.dicoding.mybottomnavtest.databinding.ItemNewsBinding

class NewsAdapter(val context: Context?, private val items: List<ArticleData>) :
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

                binding.root.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("title", this.title)
                    bundle.putString("date", this.date)
                    bundle.putString("author", this.author)
                    bundle.putString("content", this.content)
                    bundle.putInt("image", this.image)
                    bundle.putBoolean("isFavorite", this.isFavorite)

                    val intent = Intent(context, DetailArticleActivity::class.java)
                    intent.putExtras(bundle)
                    context?.startActivity(intent)
                }
            }
        }
    }
}