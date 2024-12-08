package com.dicoding.mybottomnavtest.FragmentEvents


import HomeArticleAdapter
import android.content.Intent
import android.os.Bundle
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
import com.dicoding.mybottomnavtest.ArticleActivity
import com.dicoding.mybottomnavtest.MainActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.RecipeActivity
import com.dicoding.mybottomnavtest.SpiceActivity
import com.dicoding.mybottomnavtest.adapter.HomeAdapter
import com.dicoding.mybottomnavtest.adapter.HomeRecipeAdapter
import com.dicoding.mybottomnavtest.adapter.HomeSpiceAdapter
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.data.ListEventsItem
import com.dicoding.mybottomnavtest.databinding.FragmentHomeBinding
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import com.dicoding.mybottomnavtest.viewmodel.NewsViewModel
import com.dicoding.mybottomnavtest.viewmodel.UserViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var progressBar: ProgressBar
    private val eventsList = mutableListOf<ListEventsItem>()
    private lateinit var userViewModel: UserViewModel
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var homeRecipeAdapter: HomeRecipeAdapter
    private lateinit var homeSpiceAdapter: HomeSpiceAdapter
    private lateinit var homeArticleAdapter: HomeArticleAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //RECIPES
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        progressBar = binding.loading
        binding.rvRecipesHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeRecipeAdapter = HomeRecipeAdapter(emptyList())
        binding.rvRecipesHome.adapter = homeRecipeAdapter

        newsViewModel.RecipeLiveData.observe(viewLifecycleOwner) { homeRecipe ->
            homeRecipeAdapter = HomeRecipeAdapter(homeRecipe)
            binding.rvRecipesHome.adapter = homeRecipeAdapter
            binding.loading.visibility = View.GONE
        }
        //RECIPES

        //SPICES
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        progressBar = binding.loading
        binding.rvSpicesHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeSpiceAdapter = HomeSpiceAdapter(emptyList())
        binding.rvSpicesHome.adapter = homeSpiceAdapter

        newsViewModel.SpicesLiveData.observe(viewLifecycleOwner) { spiceList ->
            homeSpiceAdapter.updateData(spiceList)
            binding.loading.visibility = View.GONE
        }

        //SPICES

        //ARTICLE
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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


        //ARTICLE

        newsViewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            homeArticleAdapter.updateData(emptyList())
            binding.loading.visibility = View.GONE
        }



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
        newsViewModel.fetchSpicesHome()
        newsViewModel.fetchArticlesHome()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSeeAllRecipes.setOnClickListener(this)
        binding.tvSeeAllSpices.setOnClickListener(this)
        binding.tvSeeAllArticles.setOnClickListener(this)

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

//        if (filteredList.isEmpty()) {
//            binding.noDataMessage.visibility = View.VISIBLE
//        } else {
//            binding.noDataMessage.visibility = View.GONE
//        }
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
}

