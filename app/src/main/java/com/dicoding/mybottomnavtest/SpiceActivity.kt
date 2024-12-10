package com.dicoding.mybottomnavtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.Response.SpicesItem
import com.dicoding.mybottomnavtest.adapter.SpiceAdapter
import com.dicoding.mybottomnavtest.databinding.ActivitySpiceBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel

class SpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpiceBinding
    private lateinit var spiceAdapter: SpiceAdapter
    private val newsViewModel: NewsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSpices.layoutManager = LinearLayoutManager(this)
        spiceAdapter = SpiceAdapter(emptyList()) { spice -> openSpiceDetail(spice) }
        binding.rvSpices.adapter = spiceAdapter

        newsViewModel.SpicesLiveData.observe(this) { spices ->
            spiceAdapter = SpiceAdapter(spices) { spice -> openSpiceDetail(spice) }
            binding.rvSpices.adapter = spiceAdapter
            binding.loading.visibility = View.GONE
        }

        binding.rvSpices.layoutManager = GridLayoutManager(this, 2)
        setSearchView(binding.searchSpice)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.loading.visibility = View.VISIBLE
        newsViewModel.fetchSpices()
    }

    private fun openSpiceDetail(spice: SpicesItem) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra("RECIPE_ID", spice.id)
        startActivity(intent)
    }


    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnSearchClickListener {
            binding.searchSpice.queryHint = "Cari Bahan Jamu Disini"
            binding.ivBack.visibility = View.GONE
            binding.tvSpiceHeader.visibility = View.GONE
        }

        searchView.setOnCloseListener {
            binding.ivBack.visibility = View.VISIBLE
            binding.tvSpiceHeader.visibility = View.VISIBLE
            false
        }
    }
}