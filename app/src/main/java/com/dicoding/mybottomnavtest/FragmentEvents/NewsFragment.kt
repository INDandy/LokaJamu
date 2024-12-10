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
import com.dicoding.mybottomnavtest.ArticleActivity
import com.dicoding.mybottomnavtest.ArticleDetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.ArticlesAdapter
import com.dicoding.mybottomnavtest.adapter.LatestNewsAdapter
import com.dicoding.mybottomnavtest.databinding.FragmentNewsBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel


class NewsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var latestNewsAdapter: LatestNewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.tvSeeAll.setOnClickListener(this)

        binding.rvArticleList.layoutManager = LinearLayoutManager(requireContext())
        articlesAdapter = ArticlesAdapter(emptyList()) { article -> openArticleDetail(article) }
        binding.rvArticleList.adapter = articlesAdapter

        newsViewModel.articlesLiveData.observe(viewLifecycleOwner) { articles ->
            articlesAdapter = ArticlesAdapter(articles) { article -> openArticleDetail(article) }
            binding.rvArticleList.adapter = articlesAdapter
            binding.loading.visibility = View.GONE
        }

        binding.rvLatestArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        latestNewsAdapter = LatestNewsAdapter(emptyList())
        binding.rvLatestArticle.adapter = latestNewsAdapter

        newsViewModel.latestNewsLiveData.observe(viewLifecycleOwner) { latestNews ->
            latestNewsAdapter = LatestNewsAdapter(latestNews)
            binding.rvLatestArticle.adapter = latestNewsAdapter
            binding.loading.visibility = View.GONE
        }

        newsViewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            binding.loading.visibility = View.GONE
        }

        binding.loading.visibility = View.VISIBLE
        newsViewModel.fetchArticles()
        newsViewModel.fetchLatestNews()

        return binding.root
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.tv_see_all -> {
                val intent = Intent(context, ArticleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun openArticleDetail(article: ArticlesItem) {
        val intent = Intent(requireContext(), ArticleDetailActivity::class.java)
        intent.putExtra("article_id", article.id)
        startActivity(intent)
    }
}




