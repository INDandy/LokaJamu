package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.api.ApiClient
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _articlesLiveData = MutableLiveData<List<ArticlesItem>>()
    val articlesLiveData: LiveData<List<ArticlesItem>> get() = _articlesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    val latestNewsLiveData = MutableLiveData<List<ArticlesItem>>()

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = ApiClient.getApiService().getArticles()
                if (response.isSuccessful) {
                    _articlesLiveData.postValue((response.body()?.data?.articles ?: emptyList()) as List<ArticlesItem>?)
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
                val response = ApiClient.getApiService().getArticles()
                if (response.isSuccessful) {
                    latestNewsLiveData.postValue((response.body()?.data?.articles ?: emptyList()) as List<ArticlesItem>?)
                } else {
                    _errorLiveData.postValue("Failed to load latest news")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
}

