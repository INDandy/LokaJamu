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

        val imagePath = intent.getStringExtra("imagePath")

        // Cek jika imagePath tidak null dan tampilkan gambar
        if (imagePath != null) {
            Glide.with(this)
                .load(imagePath)
                .into(binding.imageView)
        } else {
            // Jika imagePath null, tampilkan gambar placeholder
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        // Setel tindakan untuk tombol kembali
        binding.backButton.setOnClickListener {
            onBackPressed() // Kembali ke activity sebelumnya
        }
    }
}
