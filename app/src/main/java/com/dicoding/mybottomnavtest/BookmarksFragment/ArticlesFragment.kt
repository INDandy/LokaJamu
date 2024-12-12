package com.dicoding.mybottomnavtest.BookmarksFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.ArticlesAdapter
import com.dicoding.mybottomnavtest.database.AppDatabase
import com.dicoding.mybottomnavtest.database.Article
import com.dicoding.mybottomnavtest.database.ArticleDao

class ArticlesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ArticlesAdapter
    private val articles = mutableListOf<ArticlesItem>()

    private lateinit var database: AppDatabase
    private lateinit var articleDao: ArticleDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_articles, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        database = AppDatabase.getDatabase(requireContext())
        articleDao = database.articleDao()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ArticlesAdapter(articles) { article ->
            article.id?.let { navigateToDetail(it) }
        }
        recyclerView.adapter = adapter

        observeArticles()
    }

    fun Article.toArticlesItem(): ArticlesItem {
        return ArticlesItem(
            id = this.id,
            title = this.title,
            imageUrl = this.imageUrl,
            createdAt = this.createdAt
        )
    }

    private fun observeArticles() {
        articleDao.getAllArticles().observe(viewLifecycleOwner, Observer { articlesList ->
            progressBar.visibility = View.GONE

            adapter.updateArticles(articlesList.map { it.toArticlesItem() })
        })
    }

    private fun navigateToDetail(articleId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EVENT_ID", articleId)
        startActivity(intent)
    }
}




