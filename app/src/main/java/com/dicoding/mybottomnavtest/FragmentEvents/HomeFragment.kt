package com.dicoding.mybottomnavtest.FragmentEvents


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.HomeAdapter
import com.dicoding.mybottomnavtest.adapter.HomeRvAdapter
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.data.ListEventsItem
import com.dicoding.mybottomnavtest.databinding.FragmentHomeBinding
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var eventAdapter: HomeRvAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var progressBar: ProgressBar
    private val eventsList = mutableListOf<ListEventsItem>()
    private val filteredEventsList = mutableListOf<ListEventsItem>()
    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressBar = view?.findViewById(R.id.progressBar) ?: ProgressBar(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = HomeRvAdapter(eventsList, this)
        homeAdapter = HomeAdapter(eventsList, this)
        homeAdapter = HomeAdapter(filteredEventsList, this)
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = eventAdapter

        fetchUserDetails()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterEvents(it)
                }
                return true
            }
        })

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocus()
        }
    }

    private fun fetchUserDetails() {
        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            val token = userPreference.getToken()

            if (!token.isNullOrEmpty()) {
                try {
                    val apiService = ApiClient.getApiService()
                    val response = apiService.getUserDetails("Bearer $token")

                    if (response.isSuccessful) {
                        val firstName = response.body()?.data?.firstName
                        if (!firstName.isNullOrEmpty()) {
                            binding.newsName.text = "$firstName"
                        } else {
                            showToast("User name is empty.")
                        }
                    } else {
                        showToast("Failed to fetch user details. Status: ${response.code()}")
                    }
                } catch (e: Exception) {
                    showToast("An error occurred: ${e.message}")
                }
            } else {
                showToast("Token is missing.")
            }
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

        if (filteredList.isEmpty()) {
            binding.noDataMessage.visibility = View.VISIBLE
        } else {
            binding.noDataMessage.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
