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

        // Menampilkan gambar menggunakan Glide jika path tersedia
        if (imagePath != null) {
            Glide.with(this)
                .load(imagePath)
                .into(binding.imageView)
        } else {
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        // Membuat Bundle berisi data kunyit
        val data = Bundle().apply {
            putString("name", "Kunyit")
            putString("description", "Kunyit adalah rempah yang terkenal dengan kandungan curcuminnya yang memiliki banyak manfaat untuk kesehatan.")
            putString("benefits", "Manfaat kunyit termasuk meningkatkan kesehatan pencernaan, mengurangi peradangan, dan memperbaiki kondisi kulit.")
            putStringArrayList("jamulist", arrayListOf("Jamu Kunyit Asam", "Jamu Kunyit Putih", "Jamu Kunyit Jahe"))
        }

        // Menampilkan bottom sheet dengan data yang telah dikirim
        showContent(data)
    }

    private fun showContent(data: Bundle?) {
        // Membuat objek BottomSheetCameraResult dan mengirimkan Bundle
        val bottomSheet = BottomSheetCameraResult(data)
        bottomSheet.isCancelable = false
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}

