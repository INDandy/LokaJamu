package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.adapter.NewsAdapter
import com.dicoding.mybottomnavtest.data.ArticleData
import com.dicoding.mybottomnavtest.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSearchView(binding.searchArticle)
        binding.rvArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        setAdapter()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnSearchClickListener {
            binding.searchArticle.queryHint = "Cari Artikel Disini"
            binding.ivBack.visibility = View.GONE
            binding.tvArticleHeader.visibility = View.GONE
        }

        searchView.setOnCloseListener {
            binding.ivBack.visibility = View.VISIBLE
            binding.tvArticleHeader.visibility = View.VISIBLE
            false
        }
    }

    private fun setAdapter() {
        val dataList:MutableList<ArticleData> = mutableListOf()

        articleTitleDummy().forEachIndexed { index, title ->
            dataList.add(
                ArticleData(index, articleImageDummy()[index], title, articleDateDummy()[index], articleAuthorDummy()[index], articleContentDummy()[index])
            )
        }

        articleAdapter = NewsAdapter(dataList)
        binding.rvArticle.adapter = articleAdapter
    }

    private fun articleImageDummy(): List<Int> {
        return listOf(
            R.drawable.image_article_1,
            R.drawable.image_article_2,
            R.drawable.image_article_3,
            R.drawable.image_article_3,
            R.drawable.image_article_3
        )
    }

    private fun articleTitleDummy(): Array<String> {
        return resources.getStringArray(R.array.articleTitle)
    }

    private fun articleDateDummy(): Array<String> {
        return resources.getStringArray(R.array.articleDate)
    }

    private fun articleAuthorDummy(): Array<String> {
        return resources.getStringArray(R.array.articleAuthor)
    }

    private fun articleContentDummy(): Array<String> {
        return resources.getStringArray(R.array.articleContent)
    }
}