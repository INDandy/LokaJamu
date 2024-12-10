package com.dicoding.mybottomnavtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.R

class ArticleHomeDetailAdapter(
    private var articles: List<ArticlesItem>,
    private val onItemClick: (ArticlesItem) -> Unit
) : RecyclerView.Adapter<ArticleHomeDetailAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.iv_home_horizontal)
        private val titleTextView: TextView = view.findViewById(R.id.tv_home_horizontal)

        fun bind(article: ArticlesItem) {
            Glide.with(itemView.context)
                .load(article.imageUrl)
                .placeholder(R.drawable.no_content)
                .error(R.drawable.error_image)
                .into(imageView)

            titleTextView.text = article.title ?: "No Title"

            itemView.setOnClickListener {
                onItemClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_horizontal, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    fun updateArticles(newArticles: List<ArticlesItem>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}
