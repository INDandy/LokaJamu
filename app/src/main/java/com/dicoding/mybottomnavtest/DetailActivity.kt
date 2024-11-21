package com.dicoding.mybottomnavtest

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.api.EventApiService
import com.dicoding.mybottomnavtest.dao.EventDao
import com.dicoding.mybottomnavtest.data.Event
import com.dicoding.mybottomnavtest.data.EventDetail

import com.dicoding.mybottomnavtest.database.AppDatabase
import com.dicoding.mybottomnavtest.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var eventDetail: EventDetail
    private var eventId: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var isErrorShown = false
    private lateinit var database: AppDatabase
    private lateinit var eventDao: EventDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("FAVORITE_EVENTS", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        database = AppDatabase.getDatabase(applicationContext)
        eventDao = database.eventDao()

        eventId = intent.getIntExtra("EVENT_ID", 0)

        binding.progressBar.visibility = View.VISIBLE

        if (isConnectedToInternet()) {
            fetchEventDetail(eventId)
            binding.fabFavorite.visibility = View.VISIBLE
        } else {
            binding.fabFavorite.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, "Tidak ada koneksi Internet", Toast.LENGTH_SHORT).show()
        }

        binding.fabFavorite.setOnClickListener {
            toggleFavorite(eventDetail.event)
        }
    }

    override fun onResume() {
        super.onResume()

        if (isConnectedToInternet()) {
            fetchEventDetail(eventId)
            checkIfFavorite(eventId)
        } else {
            binding.fabFavorite.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun fetchEventDetail(eventId: Int) {
        binding.progressBar.visibility = View.VISIBLE
        binding.fabFavorite.visibility = View.GONE

        lifecycleScope.launch {

            kotlinx.coroutines.delay(500)

            val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(EventApiService::class.java)

            apiService.getEventDetail(eventId).enqueue(object : Callback<EventDetail> {
                override fun onResponse(call: Call<EventDetail>, response: Response<EventDetail>) {
                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        eventDetail = response.body()!!
                        updateUI(eventDetail.event)

                        binding.fabFavorite.visibility = View.VISIBLE
                        isErrorShown = false
                    } else {
                        if (!isErrorShown) {
                            Toast.makeText(
                                this@DetailActivity,
                                "Failed to load event detail",
                                Toast.LENGTH_SHORT
                            ).show()
                            isErrorShown = true
                        }
                    }
                }

                override fun onFailure(call: Call<EventDetail>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    if (!isErrorShown) {
                        Toast.makeText(
                            this@DetailActivity,
                            "Error: Tidak ada koneksi Internet",
                            Toast.LENGTH_SHORT
                        ).show()
                        isErrorShown = true
                    }
                }
            })
        }
    }

    private fun updateUI(event: Event) {
        binding.eventName.text = event.name
        binding.eventOrganizer.text = event.ownerName
        binding.eventTime.text = getString(R.string.event_time_format, event.beginTime, event.endTime)
        binding.eventQuota.text = getString(R.string.event_quota_format, event.quota)
        binding.eventDescription.text = getString(R.string.event_description_format, HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())

        val availableSlots = event.quota - event.registrants
        val quotaStatus = if (availableSlots > 0) {
            "$availableSlots slot(s) available"
        } else {
            "Fully booked"
        }

        binding.eventQuota.text = quotaStatus

        Glide.with(this)
            .load(event.imageLogo)
            .into(binding.eventImage)

    }

    private fun toggleFavorite(event: Event) {
        lifecycleScope.launch {
            if (isFavorite(event.id)) {
                eventDao.delete(event.id)
                binding.fabFavorite.setImageResource(R.drawable.favorite_border)
                Toast.makeText(this@DetailActivity, "Dihapus dari Favorit", Toast.LENGTH_SHORT).show()
            } else {
                eventDao.insert(event.copy(isFavorite = true))
                binding.fabFavorite.setImageResource(R.drawable.favorite)
                Toast.makeText(this@DetailActivity, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun isFavorite(eventId: Int): Boolean {
        return eventDao.isFavorite(eventId) > 0
    }

    private fun checkIfFavorite(eventId: Int) {
        lifecycleScope.launch {
            if (isFavorite(eventId)) {
                binding.fabFavorite.setImageResource(R.drawable.favorite)
            } else {
                binding.fabFavorite.setImageResource(R.drawable.favorite_border)
            }
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
