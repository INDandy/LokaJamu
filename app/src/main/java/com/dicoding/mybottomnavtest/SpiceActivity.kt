package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.mybottomnavtest.adapter.HomeSpiceAdapter
import com.dicoding.mybottomnavtest.databinding.ActivitySpiceBinding
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel

class SpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpiceBinding
    private lateinit var homeSpiceAdapter: HomeSpiceAdapter
    private lateinit var progressBar: ProgressBar

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpices()

        binding.loading.visibility = View.VISIBLE

        newsViewModel.fetchSpices()
    }

    private fun setupSpices() {
        progressBar = binding.loading
        binding.rvSpices.layoutManager = GridLayoutManager(this, 2)
        homeSpiceAdapter = HomeSpiceAdapter(emptyList())

        binding.rvSpices.adapter = homeSpiceAdapter

        newsViewModel.SpicesLiveData.observe(this) { homeSpice ->
            if (homeSpice.isNotEmpty()) {
                homeSpiceAdapter.updateData(homeSpice)
            } else {
                homeSpiceAdapter.updateData(emptyList())
            }
            binding.loading.visibility = View.GONE
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        setSearchView(binding.searchSpice)
    }

    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnSearchClickListener {
            binding.searchSpice.queryHint = "Cari Rempah Rempah Jamu Disini"
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