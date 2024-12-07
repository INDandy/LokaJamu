package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.api.ApiClient
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var ivArticleDetail: ImageView
    private lateinit var tvArticleTitleDetail: TextView
    private lateinit var tvArticleAuthor: TextView
    private lateinit var tvArticleDateDetail: TextView
    private lateinit var tvArticleContent: TextView
    private lateinit var tvArticleTags: ChipGroup
    private lateinit var fabBookmark: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        ivArticleDetail = findViewById(R.id.iv_article_detail)
        tvArticleTitleDetail = findViewById(R.id.tv_article_title_detail)
        tvArticleAuthor = findViewById(R.id.tv_article_author)
        tvArticleDateDetail = findViewById(R.id.tv_article_date_detail)
        tvArticleContent = findViewById(R.id.tv_article_content)
        tvArticleTags = findViewById(R.id.tv_article_tags)
        fabBookmark = findViewById(R.id.fab_bookmark)
        progressBar = findViewById(R.id.progressBar)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Article Detail")

        val articleId = intent.getIntExtra("EVENT_ID", -1)

        if (articleId != -1) {
            progressBar.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = ApiClient.getApiService().getArticleById(articleId)

                    if (response.isSuccessful) {
                        val article = response.body()?.data?.article // Now accessing the article directly

                        progressBar.visibility = View.GONE

                        article?.let {
                            tvArticleTitleDetail.text = it.title
                            tvArticleAuthor.text = "Author: ${it.tags?.firstOrNull() ?: "Unknown"}"
                            tvArticleDateDetail.text = "Published: ${it.createdAt}"
                            tvArticleContent.text = it.contents?.joinToString("\n") { content -> content?.text ?: "" }

                            Glide.with(this@DetailActivity)
                                .load(it.imageUrl)
                                .placeholder(R.drawable.ic_profile_black) // Optional placeholder
                                .into(ivArticleDetail)

                            it.tags?.forEach { tag ->
                                val chip = Chip(this@DetailActivity).apply {
                                    text = tag
                                    isCheckable = false
                                }
                                tvArticleTags.addView(chip)
                            }
                        }
                    } else {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailActivity, "Failed to load article", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@DetailActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Invalid article ID", Toast.LENGTH_SHORT).show()
        }
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
}



