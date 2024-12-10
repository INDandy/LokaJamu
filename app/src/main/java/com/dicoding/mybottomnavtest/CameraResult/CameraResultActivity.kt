package com.dicoding.mybottomnavtest.CameraResult

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.ActivityCameraResultBinding

class CameraResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil imagePath dari intent
        val imagePath = intent.getStringExtra("imagePath")

        // Cek apakah imagePath tidak null, lalu tampilkan gambar
        if (imagePath != null) {
            Glide.with(this)
                .load(imagePath)
                .into(binding.imageView)
        } else {
            // Jika imagePath null, tampilkan gambar placeholder
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        // Ambil data tambahan dari intent (Bundle)
        val data = intent.extras
        // Panggil showContent untuk menampilkan bottom sheet dengan data tersebut
        showContent(data)
    }

    private fun showContent(data: Bundle?) {
        val bottomSheet = BottomSheetCameraResult(data)
        bottomSheet.isCancelable = false
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

}
