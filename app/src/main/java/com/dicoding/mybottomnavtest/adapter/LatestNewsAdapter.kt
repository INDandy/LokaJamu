package com.dicoding.mybottomnavtest.adapter

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.databinding.ItemLatestNewsBinding

class LatestNewsAdapter(
    private var items: List<ArticlesItem>
    ) : RecyclerView.Adapter<LatestNewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemLatestNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLatestNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items[position]
        with(holder.binding) {
            tvLatestArticleTitle.text = article.title

            Glide.with(ivLatestArticle.context)
                .load(article.imageUrl)
                .into(ivLatestArticle)

            val exposure = 0.7f
            adjustExposure(ivLatestArticle, exposure)

            root.setOnClickListener {
                val articleId = article.id
                val context = root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", articleId)
                context.startActivity(intent)
            }
        }
    }

    private fun adjustExposure(iv: ImageView, value: Float) {
        val colorMatrix = ColorMatrix()
        colorMatrix.set(
            floatArrayOf(
                value, 0f, 0f, 0f, 0f,
                0f, value, 0f, 0f, 0f,
                0f, 0f, value, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        iv.colorFilter = colorFilter
    }
}

