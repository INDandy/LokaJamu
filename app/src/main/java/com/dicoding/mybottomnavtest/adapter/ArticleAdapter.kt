package com.dicoding.mybottomnavtest.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.R


class ArticlesAdapter(
    private var articles: List<ArticlesItem>,
    private val onItemClick: (ArticlesItem) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.iv_article)
        private val titleTextView: TextView = view.findViewById(R.id.tv_news_article_title)
        private val dateTextView: TextView = view.findViewById(R.id.tv_article_date)

        fun bind(article: ArticlesItem) {
            Glide.with(itemView.context)
                .load(article.imageUrl)
                .into(imageView)

            titleTextView.text = article.title
            dateTextView.text = article.createdAt

            itemView.setOnClickListener {
                val articleId = article.id
                val context = itemView.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", articleId)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
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

