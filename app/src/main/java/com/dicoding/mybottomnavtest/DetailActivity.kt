package com.dicoding.mybottomnavtest

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.NewsResponse.ArticleDetail
import com.dicoding.mybottomnavtest.NewsResponse.ContentItem
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.database.AppDatabase
import com.dicoding.mybottomnavtest.database.Article
import com.dicoding.mybottomnavtest.database.ArticleDao
import com.dicoding.mybottomnavtest.databinding.ActivityDetailArticleBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var database: AppDatabase
    private lateinit var articleDao: ArticleDao
    private var articleDetail: ArticleDetail? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        adjustImageExposure(binding.ivArticleDetail, 0.6f)

        database = AppDatabase.getDatabase(applicationContext)
        articleDao = database.articleDao()

        val articleId = intent.getIntExtra("EVENT_ID", -1)
        if (articleId != -1) {
            loadArticleDetails(articleId)
        } else {
            showErrorToast("Invalid article ID")
        }

        binding.fabBookmark.setOnClickListener {
            articleDetail?.let { article ->
                toggleBookmark(article)
            }
        }
    }

    private fun toggleBookmark(article: ArticleDetail) {
        lifecycleScope.launch {
            val isFavorite = articleDao.isFavorite(article.id ?: -1)
            if (isFavorite > 0) {
                articleDao.delete(article.id ?: -1)
                binding.fabBookmark.setImageResource(R.drawable.favorite_border)
                showToast("Removed from favorites")
            } else {
                articleDao.insert(article.toArticle())
                binding.fabBookmark.setImageResource(R.drawable.favorite)
                showToast("Added to favorites")
            }
        }
    }

    private suspend fun isFavorite(articleId: Int): Boolean {
        return articleDao.isFavorite(articleId) > 0
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadArticleDetails(articleId: Int) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.ArticleApiService().getArticleById(articleId)
                if (response.isSuccessful) {
                    val article = response.body()?.data?.article
                    article?.let {
                        articleDetail = it
                        updateUIWithArticleDetails(it)
                    } ?: showErrorToast("Article not found")
                } else {
                    showErrorToast("Failed to load article")
                }
            } catch (e: Exception) {
                showErrorToast("Error: ${e.message}")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIWithArticleDetails(article: ArticleDetail) {
        supportActionBar?.title = article.title
        binding.tvArticleTitleDetail.text = article.title
        binding.tvArticleDateDetail.text = "Published: ${article.createdAt}"
        binding.tvArticleContent.text = article.contents?.joinToString("\n") { it?.text ?: "" }

        Glide.with(this)
            .load(article.imageUrl)
            .placeholder(R.drawable.ic_profile_black)
            .into(binding.ivArticleDetail)

        displayTags(article.tags)
    }

    private fun displayTags(tags: List<String?>?) {
        binding.tvArticleTags.removeAllViews()

        tags?.forEach { tag ->
            val chip = Chip(this@DetailActivity).apply {
                text = tag
                isCheckable = false
                setTextColor(getColorStateList(R.color.hijau))
                chipBackgroundColor = getColorStateList(R.color.hijau_muda)
                chipStrokeColor = getColorStateList(R.color.hijau)
                chipStrokeWidth = 3f
            }
            binding.tvArticleTags.addView(chip)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showInvalidArticleMessage() {
        Toast.makeText(this, "Invalid article ID", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun adjustImageExposure(iv: ImageView, value: Float) {
        val colorMatrix = ColorMatrix().apply {
            set(
                floatArrayOf(
                    value, 0f, 0f, 0f, 0f,
                    0f, value, 0f, 0f, 0f,
                    0f, 0f, value, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }
        iv.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    private fun ArticleDetail.toArticle(): Article {
        return Article(
            id = this.id ?: -1,
            title = this.title ?: "",
            tags = this.tags ?: emptyList(),
            imageUrl = this.imageUrl,
            contents = this.contents?.map {
                ContentItem(text = it?.text, type = it?.type)
            } ?: emptyList(),
            createdAt = this.createdAt ?: "",
            updatedAt = this.updatedAt ?: "",
            isFavorite = true
        )
    }
}






