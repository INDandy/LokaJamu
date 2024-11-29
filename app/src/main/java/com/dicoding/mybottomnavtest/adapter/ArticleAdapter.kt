package com.dicoding.mybottomnavtest.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.data.Article


class ArticleAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.articleTitle)
        val imageView: ImageView = view.findViewById(R.id.articleImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.imageView.setImageResource(article.imageResId)
    }

    override fun getItemCount(): Int = articles.size
}