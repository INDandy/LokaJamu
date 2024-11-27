package com.dicoding.mybottomnavtest.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.data.ArticleData
import com.dicoding.mybottomnavtest.databinding.ItemLatestNewsBinding

class LatestNewsAdapter(private val items: List<ArticleData>) :
    RecyclerView.Adapter<LatestNewsAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemLatestNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLatestNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.tvLatestArticleTitle.text = this.title
                binding.ivLatestArticle.setImageResource(this.image)

                //set image exposure
                val exposure = 0.7f
                adjustExposure(binding.ivLatestArticle, exposure)
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