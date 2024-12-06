package com.dicoding.mybottomnavtest

import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

//    // Binding untuk layout activity
//    private lateinit var binding: ActivityDetailArticleBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Menginisialisasi ViewBinding
//        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val articleId = intent.getIntExtra("ARTICLE_ID", -1)
//
//        if (articleId != -1) {
//            fetchArticleDetails(articleId)
//        } else {
//            Toast.makeText(this, "Article not found", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun fetchArticleDetails(articleId: Int) {
//        // Menampilkan progress bar sementara API call
//        binding.progressBar.visibility = View.VISIBLE
//
//        // Mendapatkan API Service
//        val apiService = ApiClient.getApiService()
//
//        // Panggil API untuk mendapatkan artikel berdasarkan ID
//        apiService.getArticleById(articleId).enqueue(object : Callback<NewsResponse> {
//            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
//                // Sembunyikan progress bar setelah mendapat response
//                binding.progressBar.visibility = View.GONE
//
//                if (response.isSuccessful) {
//                    val article =
//                        response.body()?.data?.article  // Navigate through the 'data' object
//                    article?.let {
//                        // Menampilkan data artikel di UI menggunakan ViewBinding
//                        Glide.with(this@DetailActivity)
//                            .load(it.imageUrl)
//                            .into(binding.ivArticleDetail)
//
//                        binding.tvArticleTitleDetail.text = it.title
//                        binding.tvArticleDateDetail.text = it.createdAt
//
//                        // Menggabungkan konten artikel yang berupa list
//                        // We check if the contents list is not null and is not empty
//                        val contentText = it.contents?.joinToString("\n\n") { content ->
//                            content?.text ?: ""
//                        }
//                        binding.tvArticleContent.text = contentText
//
//                        // Menampilkan tags artikel menggunakan ChipGroup
//                        binding.tvArticleTags.removeAllViews()  // Clear previous tags
//                        it.tags?.forEach { tag ->
//                            val chip = Chip(this@DetailActivity)
//                            chip.text = tag ?: "No Tag"
//                            binding.tvArticleTags.addView(chip)
//                        }
//                    }
//                } else {
//                    // Jika response tidak berhasil, tampilkan pesan kesalahan
//                    Toast.makeText(
//                        this@DetailActivity,
//                        "Failed to load article",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                // Sembunyikan progress bar jika terjadi kegagalan API call
//                binding.progressBar.visibility = View.GONE
//                Toast.makeText(this@DetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }
}

