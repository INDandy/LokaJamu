package com.dicoding.mybottomnavtest

import androidx.appcompat.app.AppCompatActivity

class ArticleActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityArticleBinding
//    private lateinit var articleAdapter: NewsAdapter
//    private lateinit var filteredList: ArrayList<ArticleData>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityArticleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.rvArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        setAdapter()
//        setSearchView(binding.searchArticle)
//
//        binding.ivBack.setOnClickListener {
//            finish()
//        }
//    }
//
//    private fun setSearchView(searchView: androidx.appcompat.widget.SearchView) {
//        searchView.setOnSearchClickListener {
//            binding.searchArticle.queryHint = "Cari Artikel Disini"
//            binding.ivBack.visibility = View.GONE
//            binding.tvArticleHeader.visibility = View.GONE
//        }
//
//        searchView.setOnCloseListener {
//            binding.ivBack.visibility = View.VISIBLE
//            binding.tvArticleHeader.visibility = View.VISIBLE
//            false
//        }
//    }
//
//    private fun setAdapter() {
//        val dataList:MutableList<ArticleData> = mutableListOf()
//
//        articleTitleDummy().forEachIndexed { index, title ->
//            dataList.add(
//                ArticleData(index, articleImageDummy()[index], title, articleDateDummy()[index], articleAuthorDummy()[index], articleContentDummy()[index])
//            )
//        }
//
//        articleAdapter = NewsAdapter(this, dataList)
//        binding.rvArticle.adapter = articleAdapter
//
//        setAdapterOnSearch(dataList)
//    }
//
//    private fun setAdapterOnSearch(data: MutableList<ArticleData>) {
//        filteredList = ArrayList(data)
//
//        binding.searchArticle.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onQueryTextChange(newText: String?): Boolean {
//                data.clear()
//                val searchText = newText!!.lowercase(Locale.getDefault())
//
//                if (searchText.isNotEmpty()) {
//                    filteredList.forEach {
//                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)) {
//                            data.add(it)
//                        }
//                    }
//                    articleAdapter.notifyDataSetChanged()
//
//                    if(data.isEmpty()) {
//                        Snackbar.make(binding.root, "Artikel Tidak Ditemukan", Snackbar.LENGTH_SHORT)
//                            .setAction("Hapus") { binding.searchArticle.setQuery("", false) }
//                            .setActionTextColor(ContextCompat.getColor(binding.root.context, R.color.oranye))
//                            .show()
//                    }
//                }
//                else {
//                    data.clear()
//                    data.addAll(filteredList)
//                    articleAdapter.notifyDataSetChanged()
//                }
//                return false
//            }
//        })
//    }
//
//    private fun articleImageDummy(): List<Int> {
//        return listOf(
//            R.drawable.image_article_1,
//            R.drawable.image_article_2,
//            R.drawable.image_article_3,
//            R.drawable.image_article_3,
//            R.drawable.image_article_3
//        )
//    }
//
//    private fun articleTitleDummy(): Array<String> {
//        return resources.getStringArray(R.array.articleTitle)
//    }
//
//    private fun articleDateDummy(): Array<String> {
//        return resources.getStringArray(R.array.articleDate)
//    }
//
//    private fun articleAuthorDummy(): Array<String> {
//        return resources.getStringArray(R.array.articleAuthor)
//    }
//
//    private fun articleContentDummy(): Array<String> {
//        return resources.getStringArray(R.array.articleContent)
//    }
}