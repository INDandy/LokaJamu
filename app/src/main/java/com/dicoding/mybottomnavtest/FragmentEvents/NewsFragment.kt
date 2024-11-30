package com.dicoding.mybottomnavtest.FragmentEvents

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.ArticleActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.LatestNewsAdapter
import com.dicoding.mybottomnavtest.adapter.NewsAdapter
import com.dicoding.mybottomnavtest.data.ArticleData
import com.dicoding.mybottomnavtest.databinding.FragmentNewsBinding


class NewsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var latestArticleAdapter: LatestNewsAdapter
    private lateinit var articleAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.rvLatestArticle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        setLatestArticleAdapter()

        binding.rvArticleList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        setArticleAdapter()

        binding.tvSeeAll.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.tv_see_all -> {
                val intent = Intent(activity, ArticleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setLatestArticleAdapter() {
        val dataList: MutableList<ArticleData> = mutableListOf()

        articleTitleDummy().forEachIndexed { index, title ->
            dataList.add(
                ArticleData(
                    index,
                    articleImageDummy()[index],
                    title,
                    articleDateDummy()[index],
                    articleAuthorDummy()[index],
                    articleContentDummy()[index]
                )
            )
        }

        latestArticleAdapter = LatestNewsAdapter(dataList)
        binding.rvLatestArticle.adapter = latestArticleAdapter
    }

    private fun setArticleAdapter() {
        val dataList: MutableList<ArticleData> = mutableListOf()

        articleTitleDummy().forEachIndexed { index, title ->
            dataList.add(
                ArticleData(
                    index,
                    articleImageDummy()[index],
                    title,
                    articleDateDummy()[index],
                    articleAuthorDummy()[index],
                    articleContentDummy()[index]
                )
            )
        }

        articleAdapter = NewsAdapter(context, dataList)
        binding.rvArticleList.adapter = articleAdapter
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

