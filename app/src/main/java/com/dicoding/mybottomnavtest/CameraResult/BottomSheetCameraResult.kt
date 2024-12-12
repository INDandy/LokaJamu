package com.dicoding.mybottomnavtest.CameraResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.mybottomnavtest.adapter.SpiceJamuAdapter
import com.dicoding.mybottomnavtest.databinding.BottomSheetCameraResultBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetCameraResult(private val data: Bundle?) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetCameraResultBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var jamuList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCameraResultBinding.inflate(inflater, container, false)

        // Ambil data dari Bundle
        binding.tvSpiceHeader.text = data?.getString("name")
        binding.tvSpiceName.text = data?.getString("name")
        binding.tvSpiceDescription.text = data?.getString("description")
        binding.tvSpiceBenefits.text = data?.getString("benefits")

        // Ambil daftar jamu dari data dan set ke RecyclerView
        jamuList = data?.getStringArrayList("jamulist") ?: emptyList()
        setupRecyclerView()

        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = SpiceJamuAdapter(jamuList)
        binding.rvSpiceJamulist.adapter = adapter
    }
}

