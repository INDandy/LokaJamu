package com.dicoding.mybottomnavtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.mybottomnavtest.adapter.SpiceAdapter
import com.dicoding.mybottomnavtest.data.SpiceData
import com.dicoding.mybottomnavtest.databinding.ActivitySpiceBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class SpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpiceBinding
    private lateinit var spiceAdapter: SpiceAdapter
    private lateinit var filteredList: ArrayList<SpiceData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSpices.layoutManager = GridLayoutManager(this, 2)
        setSpiceAdapter()
        setSearchView(binding.searchSpice)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setSpiceAdapter() {
        val dataList: MutableList<SpiceData> = mutableListOf()

        spiceNameDummy().forEachIndexed { index, name ->
            dataList.add(
                SpiceData(
                    index,
                    name,
                    ImageDummy()[index],
                    spiceDescriptionDummy()[index],
                    spiceBenefitDummy()[index]
                )
            )
        }

        spiceAdapter = SpiceAdapter(this, dataList)
        binding.rvSpices.adapter = spiceAdapter
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

    private fun setAdapterOnSearch(data: MutableList<SpiceData>) {
        filteredList = ArrayList(data)

        binding.searchSpice.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                data.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())

                if (searchText.isNotEmpty()) {
                    filteredList.forEach {
                        if (it.name.lowercase(Locale.getDefault()).contains(searchText)) {
                            data.add(it)
                        }
                    }
                    spiceAdapter.notifyDataSetChanged()

                    if(data.isEmpty()) {
                        Snackbar.make(binding.root, "Bahan Jamu Tidak Ditemukan", Snackbar.LENGTH_SHORT)
                            .setAction("Hapus") { binding.searchSpice.setQuery("", false) }
                            .setActionTextColor(ContextCompat.getColor(binding.root.context, R.color.oranye))
                            .show()
                    }
                }
                else {
                    data.clear()
                    data.addAll(filteredList)
                    spiceAdapter.notifyDataSetChanged()
                }
                return false
            }
        })
    }

    private fun spiceNameDummy(): Array<String> {
        return resources.getStringArray(R.array.SpiceName)
    }
    private fun ImageDummy(): List<Int> {
        return listOf(
            R.drawable.image_article_1,
            R.drawable.image_article_2,
            R.drawable.image_article_3,
            R.drawable.image_article_3,
            R.drawable.image_article_3
        )
    }
    private fun spiceDescriptionDummy(): Array<String> {
        return resources.getStringArray(R.array.SpiceDescription)
    }
    private fun spiceBenefitDummy(): Array<String> {
        return resources.getStringArray(R.array.SpiceBenefit)
    }
}