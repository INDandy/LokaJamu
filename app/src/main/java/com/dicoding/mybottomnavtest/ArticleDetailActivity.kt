package com.dicoding.mybottomnavtest

import androidx.appcompat.app.AppCompatActivity

class ArticleDetailActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityDetailArticleBinding
//    private val apiService: ApiService by lazy { ApiClient.getApiService() }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val articleId = intent.getIntExtra("ARTICLE_ID", -1)
//        if (articleId != -1) {
//            fetchArticleDetail(articleId)
//        } else {
//            Toast.makeText(this, "Invalid Article ID", Toast.LENGTH_SHORT).show()
//            finish()
//        }
//    }
//
//    private fun fetchArticleDetail(id: Int) {
//        lifecycleScope.launch {
//            try {
//                val response = apiService.getArticleById(id)
//                if (response.isSuccessful) {
//                    response.body()?.let { article ->
//                        displayArticleDetails(article)
//                    }
//                } else {
//                    Toast.makeText(
//                        this@ArticleDetailActivity,
//                        "Failed to load article",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            } catch (e: Exception) {
//                Toast.makeText(
//                    this@ArticleDetailActivity,
//                    "Error: ${e.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//    private fun displayArticleDetails(article: ArticlesItem) {
//        binding.apply {
//            if (!article.imageUrl.isNullOrEmpty()) {
//                Glide.with(this@ArticleDetailActivity)
//                    .load(article.imageUrl)
//                    .placeholder(R.drawable.ic_profile_black)
//                    .error(R.drawable.ic_profile_black)
//                    .into(ivArticleDetail)
//            } else {
//                ivArticleDetail.setImageResource(R.drawable.ic_profile_black)
//            }
//
//            // Set detail artikel lainnya
//            tvArticleTitleDetail.text = article.title ?: "Untitled Article"
//            tvArticleDateDetail.text = article.createdAt ?: "Unknown Date"
//            tvArticleContent.text = article.contents?.joinToString("\n") { it?.toString() ?: "" }
//
//            // Set tag artikel ke dalam ChipGroup
//            tvArticleTags.removeAllViews()
//            article.tags?.forEach { tag ->
//                tag?.let {
//                    val chip = Chip(this@ArticleDetailActivity).apply {
//                        text = it
//                        isClickable = true
//                        isCheckable = false
//                        setChipBackgroundColorResource(R.color.oranye)
//                        setTextColor(ContextCompat.getColor(context, R.color.white))
//                    }
//                    tvArticleTags.addView(chip)
//                }
//            }
//        }
//    }
}
