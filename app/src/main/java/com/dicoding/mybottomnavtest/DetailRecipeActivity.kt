package com.dicoding.mybottomnavtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mybottomnavtest.FragmentDetail.BottomSheetDetailRecipe
import com.dicoding.mybottomnavtest.databinding.ActivityDetailRecipeBinding

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        binding.ivRecipeSpiceDetail.setImageResource(data?.getInt("image") ?: 0)

        showContent(data)
    }

    private fun showContent(data: Bundle?) {
        val bottomSheet = BottomSheetDetailRecipe(data)
        bottomSheet.isCancelable = false
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

}