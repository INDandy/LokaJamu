package com.dicoding.mybottomnavtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mybottomnavtest.FragmentDetail.BottomSheetDetailSpice
import com.dicoding.mybottomnavtest.databinding.ActivityDetailSpiceBinding

class DetailSpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSpiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        binding.ivSpiceDetail.setImageResource(data?.getInt("image") ?: 0)

        showContent(data)
    }

    private fun showContent(data: Bundle?) {
        val bottomSheet = BottomSheetDetailSpice(data)
        bottomSheet.isCancelable = false
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}