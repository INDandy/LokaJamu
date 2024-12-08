package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.Response.Recipe
import com.dicoding.mybottomnavtest.Response.Spice
import com.dicoding.mybottomnavtest.api.ApiClient
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _articlesLiveData = MutableLiveData<List<ArticlesItem>>()
    val articlesLiveData: LiveData<List<ArticlesItem>> get() = _articlesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    val latestNewsLiveData = MutableLiveData<List<ArticlesItem>>()

    val RecipeLiveData = MutableLiveData<List<Recipe>>()

    val SpicesLiveData = MutableLiveData<List<Spice>>()

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
    fun fetchSpicesHome() {
        viewModelScope.launch {
            try {
                val response = ApiClient.SpiceApiService().getSpice()
                if (response.isSuccessful) {
                    SpicesLiveData.postValue(
                        listOf(response.body()?.data?.spice ?: emptyList<Spice>()) as List<Spice>?)
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
                        listOf(response.body()?.data?.recipe ?: emptyList<Recipe>()) as List<Recipe>?)
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
}

