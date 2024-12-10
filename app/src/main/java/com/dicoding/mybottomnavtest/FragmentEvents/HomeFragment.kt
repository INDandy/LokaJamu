package com.dicoding.mybottomnavtest.FragmentEvents


import HomeArticleAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.mybottomnavtest.ArticleActivity
import com.dicoding.mybottomnavtest.MainActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.RecipeActivity
import com.dicoding.mybottomnavtest.SpiceActivity
import com.dicoding.mybottomnavtest.adapter.HomeAdapter
import com.dicoding.mybottomnavtest.adapter.HomeRecipeAdapter
import com.dicoding.mybottomnavtest.adapter.HomeSpiceAdapter
import com.dicoding.mybottomnavtest.adapter.SpiceViewPagerAdapter
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.data.ListEventsItem
import com.dicoding.mybottomnavtest.databinding.FragmentHomeBinding
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel
import com.dicoding.mybottomnavtest.viewmodel.UserViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), View.OnClickListener{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var userViewModel: UserViewModel
    private lateinit var homeRecipeAdapter: HomeRecipeAdapter
    private lateinit var homeSpiceAdapter: HomeSpiceAdapter
    private lateinit var homeArticleAdapter: HomeArticleAdapter
    private lateinit var spiceViewPagerAdapter: SpiceViewPagerAdapter
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private val eventsList = mutableListOf<ListEventsItem>()
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: SpiceViewPagerAdapter
    private val scrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewPager.currentItem
            val nextItem = if (currentItem + 1 < spiceViewPagerAdapter.itemCount) currentItem + 1 else 0
            viewPager.setCurrentItem(nextItem, true)
            autoScrollHandler.postDelayed(this, 3000)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecipes(inflater, container)

        setupSpices(inflater, container)

        setupArticles(inflater, container)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.userDetailsStatus.observe(viewLifecycleOwner) { status ->
            if (status == false) {
                showSessionExpiredDialog()
            }
        }

        val firstName = userViewModel.firstName.value
        if (firstName != null ) {
            binding.newsName.text = "$firstName"
        } else {
            fetchUserDetails()
        }
        binding.loading.visibility = View.VISIBLE

        newsViewModel.fetchRecipesHome()
        newsViewModel.fetchSpices()
        newsViewModel.fetchArticlesHome()

        viewPager = binding.viewPager2
        spiceViewPagerAdapter = SpiceViewPagerAdapter(emptyList())
        viewPager.adapter = spiceViewPagerAdapter

        newsViewModel.SpicesLiveData.observe(viewLifecycleOwner) { spices ->
            if (spices.isNotEmpty()) {
                spiceViewPagerAdapter = SpiceViewPagerAdapter(spices)
                viewPager.adapter = spiceViewPagerAdapter
                startAutoScroll()
            } else {
                spiceViewPagerAdapter = SpiceViewPagerAdapter(emptyList())
                viewPager.adapter = spiceViewPagerAdapter
            }
        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager2)
        adapter = SpiceViewPagerAdapter(emptyList())

        binding.tvSeeAllRecipes.setOnClickListener(this)
        binding.tvSeeAllSpices.setOnClickListener(this)
        binding.tvSeeAllArticles.setOnClickListener(this)

    }

    private fun setupRecipes(inflater: LayoutInflater, container: ViewGroup?) {

        progressBar = binding.loading
        binding.rvRecipesHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeRecipeAdapter = HomeRecipeAdapter(emptyList())
        binding.rvRecipesHome.adapter = homeRecipeAdapter

        newsViewModel.RecipeLiveData.observe(viewLifecycleOwner) { homeRecipe ->
            if (homeRecipe.isNotEmpty()) {
                homeRecipeAdapter.updateData(homeRecipe)
            } else {
                homeRecipeAdapter.updateData(emptyList())
            }
            binding.loading.visibility = View.GONE
        }
    }

    private fun setupSpices(inflater: LayoutInflater, container: ViewGroup?) {
        progressBar = binding.loading
        binding.rvSpicesHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeSpiceAdapter = HomeSpiceAdapter(emptyList())
        binding.rvSpicesHome.adapter = homeSpiceAdapter

        newsViewModel.SpicesLiveData.observe(viewLifecycleOwner) { homeSpice ->
            if (homeSpice.isNotEmpty()) {
                homeSpiceAdapter.updateData(homeSpice)
            } else {
                homeSpiceAdapter.updateData(emptyList())
            }
            binding.loading.visibility = View.GONE
        }
    }

    private fun setupArticles(inflater: LayoutInflater, container: ViewGroup?) {
        progressBar = binding.loading
        binding.rvArticlesHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeArticleAdapter = HomeArticleAdapter(emptyList())
        binding.rvArticlesHome.adapter = homeArticleAdapter

        newsViewModel.latestNewsLiveData.observe(viewLifecycleOwner) { homeArticle ->
            if (homeArticle.isNotEmpty()) {
                homeArticleAdapter.updateData(homeArticle)
            } else {
                homeArticleAdapter.updateData(emptyList())
            }
            binding.loading.visibility = View.GONE
        }
    }

    private fun fetchUserDetails() {
        showLoading(true)

        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            val token = userPreference.getToken()

            if (!token.isNullOrEmpty()) {
                try {
                    val apiService = ApiClient.UserApiService()
                    val response = apiService.getUserDetails("Bearer $token")

                    if (response.isSuccessful) {
                        val firstName = response.body()?.data?.firstName
                        if (!firstName.isNullOrEmpty()) {
                            userViewModel.setUserDetailsStatus(true)
                            binding.newsName.text = "$firstName"
                        } else {
                            showSessionExpiredDialog()
                        }
                    } else {
                        showSessionExpiredDialog()
                    }
                } catch (e: Exception) {
                    showSessionExpiredDialog()
                } finally {
                    showLoading(false)
                }
            } else {
                showLoading(false)
                showSessionExpiredDialog()
            }
        }
    }

    private fun showSessionExpiredDialog() {
        val mainActivity = activity as? MainActivity
        mainActivity?.runOnUiThread {
            AlertDialog.Builder(requireContext())
                .setTitle("Session Expired")
                .setMessage("Please log in again.")
                .setCancelable(false)
                .setPositiveButton("Log In") { _, _ ->
                    mainActivity.logout()
                }
                .show()
        }
    }

    private fun filterEvents(query: String) {
        val filteredList = if (query.isEmpty()) {
            eventsList
        } else {
            eventsList.filter {
                it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
            }
        }

        homeAdapter.updateData(filteredList)

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        autoScrollHandler.removeCallbacks(scrollRunnable)
        binding
    }

    override fun onClick(p0: View) {
        when(p0.id) {
            R.id.tv_see_all_recipes -> {
                val intent = Intent(context, RecipeActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_see_all_spices -> {
                val intent = Intent(context, SpiceActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_see_all_articles -> {
                val intent = Intent(context, ArticleActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun startAutoScroll() {
        autoScrollHandler.postDelayed(scrollRunnable, 3000)
    }

}

