package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.Response.RecipesItem
import com.dicoding.mybottomnavtest.Response.SpicesItem
import com.dicoding.mybottomnavtest.api.ApiClient
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _articlesLiveData = MutableLiveData<List<ArticlesItem>>()
    private val _errorLiveData = MutableLiveData<String>()

    private val latestDetailHome = MutableLiveData<List<ArticlesItem>>()
    val latestNews: LiveData<List<ArticlesItem>> get() = latestNewsLiveData

    val articlesLiveData: LiveData<List<ArticlesItem>> get() = _articlesLiveData
    val errorLiveData: LiveData<String> get() = _errorLiveData
    val latestNewsLiveData = MutableLiveData<List<ArticlesItem>>()
    val RecipeLiveData = MutableLiveData<List<RecipesItem>>()
    val SpicesLiveData = MutableLiveData<List<SpicesItem>>()

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = ApiClient.ArticleApiService().getArticles()
                if (response.isSuccessful) {
                    _articlesLiveData.postValue(
                        (response.body()?.data?.articles ?: emptyList()) as List<ArticlesItem>?
                    )
                } else {
                    _errorLiveData.postValue("Failed to load articles")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
    fun fetchLatestNews() {
        viewModelScope.launch {
            try {
                val response = ApiClient.ArticleApiService().getArticles()
                if (response.isSuccessful) {
                    latestNewsLiveData.postValue(
                        (response.body()?.data?.articles ?: emptyList()) as List<ArticlesItem>?
                    )
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
    fun fetchArticleDetail() {
        viewModelScope.launch {
            try {
                val response = ApiClient.ArticleApiService().getArticles()
                if (response.isSuccessful) {
                    latestNewsLiveData.postValue(
                        (response.body()?.data?.articles ?: emptyList()) as List<ArticlesItem>?
                    )
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
    fun fetchArticlesHome() {
        viewModelScope.launch {
            try {
                val response = ApiClient.ArticleApiService().getArticles()
                if (response.isSuccessful) {
                    latestNewsLiveData.postValue(
                        (response.body()?.data?.articles ?: emptyList()) as List<ArticlesItem>?
                    )
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
    fun fetchSpices() {
        viewModelScope.launch {
            try {
                val response = ApiClient.SpiceApiService().getSpice()
                if (response.isSuccessful) {
                    SpicesLiveData.postValue(
                        (response.body()?.data?.spices ?: emptyList()) as List<SpicesItem>?
                    )
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }


    fun fetchRecipesHome() {
        viewModelScope.launch {
            try {
                val response = ApiClient.RecipeApiService().getRecipes()
                if (response.isSuccessful) {
                    RecipeLiveData.postValue(
                        (response.body()?.data?.recipes ?: emptyList()) as List<RecipesItem>?
                    )
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
    private fun handleError(message: String) {
        _errorLiveData.postValue(message)
    }
}

