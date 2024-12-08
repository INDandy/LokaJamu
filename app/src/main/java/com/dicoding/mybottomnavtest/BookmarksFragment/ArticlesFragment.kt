package com.dicoding.mybottomnavtest.BookmarksFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.ArticlesAdapter

class ArticlesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ArticlesAdapter
    private val articles = mutableListOf<ArticlesItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_articles, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ArticlesAdapter(articles) { article ->
            article.id?.let { navigateToDetail(it) }
        }
        recyclerView.adapter = adapter

//        fetchBookmarkedArticles()
    }

//    private fun fetchBookmarkedArticles() {
//        lifecycleScope.launch {
//            progressBar.visibility = View.VISIBLE
//
//            try {
//                val userPreference = UserPreference.getInstance(requireContext().dataStore)
//                val token = userPreference.getToken()
//
//                if (!token.isNullOrEmpty()) {
//                    val response = ApiClient.getApiService().getBookmarkedArticles("Bearer $token")
//                    if (response.isSuccessful) {
//                        val fetchedArticles = response.body()?.data ?: listOf<ArticlesItem>()
//                        adapter.updateArticles(fetchedArticles)
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "Failed to fetch articles: ${response.code()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "Token is missing", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//            } finally {
//                progressBar.visibility = View.GONE
//            }
//        }
//    }

    private fun navigateToDetail(articleId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EVENT_ID", articleId)
        startActivity(intent)
    }
}
