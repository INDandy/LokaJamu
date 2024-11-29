package com.dicoding.mybottomnavtest.BookmarksFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.ArticleAdapter
import com.dicoding.mybottomnavtest.data.Article

class RecipesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RecipesFragment", "onViewCreated called")
        // Tambahkan log untuk memeriksa jumlah artikel


        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val articles = listOf(
            Article("Manfaat Jahe Bagi Imunitas Tubuh", R.drawable.image_article_1),
            Article("Manfaat Rutin Minum Jamu Beras Kencur", R.drawable.image_article_2),
            Article("Panduan Memilih dan Mengkonsumsi Jamu", R.drawable.image_article_3)
        )

        articleAdapter = ArticleAdapter(articles)
        recyclerView.adapter = articleAdapter
    }
}