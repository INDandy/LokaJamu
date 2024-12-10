package com.dicoding.mybottomnavtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.adapter.ArticleHomeDetailAdapter
import com.dicoding.mybottomnavtest.databinding.ActivityArticleBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleAdapter: ArticleHomeDetailAdapter
    private lateinit var filteredList: ArrayList<ArticlesItem>
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvArticle.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleHomeDetailAdapter(emptyList()) { article -> openArticleDetail(article) }
        binding.rvArticle.adapter = articleAdapter

        newsViewModel.latestNews.observe(this) { articles ->
            articleAdapter = ArticleHomeDetailAdapter(articles) { article ->
                openArticleDetail(article)
            }
            binding.rvArticle.adapter = articleAdapter
            binding.loading.visibility = View.GONE
        }


        binding.rvArticle.layoutManager = GridLayoutManager(this, 2)
        setSearchView(binding.searchArticle)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.loading.visibility = View.VISIBLE
        newsViewModel.fetchArticlesHome()
    }

    private fun openArticleDetail(recipe: ArticlesItem) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra("RECIPE_ID", recipe.id)
        startActivity(intent)
    }


    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnSearchClickListener {
            binding.searchArticle.queryHint = "Cari Bahan Jamu Disini"
            binding.ivBack.visibility = View.GONE
            binding.tvArticleHeader.visibility = View.GONE
        }

        searchView.setOnCloseListener {
            binding.ivBack.visibility = View.VISIBLE
            binding.tvArticleHeader.visibility = View.VISIBLE
            false
        }
    }
}

