package com.dicoding.mybottomnavtest.FragmentEvents


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.HomeAdapter
import com.dicoding.mybottomnavtest.adapter.HomeRvAdapter
import com.dicoding.mybottomnavtest.data.ListEventsItem
import com.dicoding.mybottomnavtest.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var eventAdapter: HomeRvAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
//    private lateinit var viewPager: ViewPager2
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
//        viewPager = binding.viewPager2

//        fetchEvents()

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

//    private fun fetchEvents() {
//        val binding = binding
//        binding?.progressBar?.visibility = View.VISIBLE
//
//        ApiClient.ApiService().getEvents().enqueue(object : Callback<ListEvent> {
//            override fun onResponse(call: Call<ListEvent>, response: Response<ListEvent>) {
//                if (response.isSuccessful) {
//                    val eventList = response.body()?.listEvents ?: emptyList()
//
//                    homeAdapter = HomeAdapter(eventList, this@HomeFragment)
////                    viewPager.adapter = homeAdapter
//
//                    val handler = Handler(Looper.getMainLooper())
//                    val runnable = object : Runnable {
//                        override fun run() {
//                            val currentItem = viewPager.currentItem
//                            val nextItem = if (currentItem == eventList.size - 1) 0 else currentItem + 1
//                            viewPager.setCurrentItem(nextItem, true)
//                            handler.postDelayed(this, 3000)
//                        }
//                    }
//                    handler.postDelayed(runnable, 3000)
//
//                    binding?.progressBar?.visibility = View.GONE
//                } else {
//                    binding?.progressBar?.visibility = View.GONE
//                }
//            }
//
//            override fun onFailure(call: Call<ListEvent>, t: Throwable) {
//                binding?.progressBar?.visibility = View.GONE
//            }
//        })
//    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}