package com.dicoding.mybottomnavtest.FragmentEvents

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.ArticleDetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.adapter.ArticlesAdapter
import com.dicoding.mybottomnavtest.adapter.LatestNewsAdapter
import com.dicoding.mybottomnavtest.databinding.FragmentNewsBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var latestNewsAdapter: LatestNewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.rvArticleList.layoutManager = LinearLayoutManager(requireContext())
        articlesAdapter = ArticlesAdapter(emptyList()) { article ->
            openArticleDetail(article)
        }
        binding.rvArticleList.adapter = articlesAdapter

        binding.rvLatestArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        latestNewsAdapter = LatestNewsAdapter(emptyList())
        binding.rvLatestArticle.adapter = latestNewsAdapter

        newsViewModel.articlesLiveData.observe(viewLifecycleOwner) { articles ->
            articlesAdapter = ArticlesAdapter(articles) { article ->
                openArticleDetail(article)
            }
            binding.rvArticleList.adapter = articlesAdapter
        }

        newsViewModel.latestNewsLiveData.observe(viewLifecycleOwner) { latestNews ->
            latestNewsAdapter = LatestNewsAdapter(latestNews)
            binding.rvLatestArticle.adapter = latestNewsAdapter
        }

        newsViewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }

        newsViewModel.fetchArticles()
        newsViewModel.fetchLatestNews()

        return binding.root
    }

    private fun openArticleDetail(article: ArticlesItem) {
        val intent = Intent(requireContext(), ArticleDetailActivity::class.java)
        intent.putExtra("article_id", article.id)
        startActivity(intent)
    }
}


