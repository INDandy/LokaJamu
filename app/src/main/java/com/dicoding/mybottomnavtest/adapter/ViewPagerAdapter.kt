package com.dicoding.mybottomnavtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.NewsResponse.ArticleViewPager
import com.dicoding.mybottomnavtest.R

class ViewPagerAdapter(private var articles: List<ArticleViewPager>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        val imageUrl = article.imageUrl

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.imageView)
        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.lokajamulogo)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = articles.size
}




