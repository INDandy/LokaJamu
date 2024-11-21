package com.dicoding.mybottomnavtest.FragmentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.finalsubmission1.data.ListEvent
import com.dicoding.finalsubmission1.data.ListEventsItem

import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.adapter.NewsAdapter
import com.dicoding.mybottomnavtest.api.ApiClient.apiService
import com.dicoding.mybottomnavtest.databinding.FragmentNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {

    private lateinit var eventAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val eventsList = mutableListOf<ListEventsItem>()
    private val _events = MutableLiveData<List<ListEventsItem>>()
    private var isErrorShown = false
    val events: LiveData<List<ListEventsItem>> = _events

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        progressBar = view?.findViewById(R.id.progressBar) ?: ProgressBar(requireContext())
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = NewsAdapter(eventsList, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = eventAdapter

        fetchEvents()
    }



    private fun fetchEvents() {

        binding.progressBar.visibility = View.VISIBLE

        apiService.getEvents().enqueue(object : Callback<ListEvent> {
            override fun onResponse(
                call: Call<ListEvent>,
                response: Response<ListEvent>
            ) {
                if (response.isSuccessful) {
                    eventsList.clear()
                    eventsList.addAll(response.body()?.listEvents ?: listOf())
                    eventAdapter.notifyDataSetChanged()
                    isErrorShown = false
                } else {
                    if (!isErrorShown) {
                        Toast.makeText(
                            context,
                            "Failed to load events",
                            Toast.LENGTH_SHORT
                        ).show()
                        isErrorShown = true
                    }
                }

                binding.progressBar.visibility = View.GONE
            }


            override fun onFailure(call: Call<ListEvent>, t: Throwable) {
                if (!isErrorShown) {
                    Toast.makeText(context, "Error: Tidak Ada Koneksi Internet", Toast.LENGTH_SHORT)
                        .show()
                    isErrorShown = true
                }
                binding.progressBar.visibility = View.VISIBLE
            }
        }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
