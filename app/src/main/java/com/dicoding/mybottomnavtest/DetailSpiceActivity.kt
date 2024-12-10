package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.FragmentDetail.BottomSheetDetailSpice
import com.dicoding.mybottomnavtest.Response.SpicesItem
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.databinding.ActivityDetailSpiceBinding
import kotlinx.coroutines.launch

class DetailSpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSpiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spiceId = intent.getIntExtra("SPICE_ID", -1)
        if (spiceId == -1) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fetchSpiceDetails(spiceId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchSpiceDetails(id: Int) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.SpiceApiService().getSpiceById(id)
                if (response.isSuccessful) {
                    val spice = response.body()?.data?.spice
                    if (spice != null) {
                        displaySpiceDetails(spice)
                    } else {
                        Log.e("API Error", "Spice not found for ID: $id")
                        Toast.makeText(this@DetailSpiceActivity, "Spice not found", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Log.e("API Error", "Failed to fetch data: ${response.errorBody()?.string()}")
                    Toast.makeText(this@DetailSpiceActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Log.e("API Exception", "Error: ${e.message}", e)
                Toast.makeText(this@DetailSpiceActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun displaySpiceDetails(spice: SpicesItem) {
        Glide.with(this)
            .load(spice.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.ivSpiceDetail)

        val bundle = Bundle().apply {
            putString("name", spice.name)
            putString("description", spice.description)
            putString("benefits", spice.benefits)
            putString("jamulist", spice.jamuList?.joinToString("\n") ?: "No Jamu List")
            putString("tags", spice.tags?.joinToString(", ") ?: "No Tags")

        }

        val bottomSheet = BottomSheetDetailSpice(bundle)
        bottomSheet.show(supportFragmentManager, BottomSheetDetailSpice::class.java.simpleName)

    }

}




