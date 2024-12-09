package com.dicoding.mybottomnavtest.repository

import com.dicoding.mybottomnavtest.NewsResponse.ArticleViewPagerResponse
import com.dicoding.mybottomnavtest.api.ApiClient
import retrofit2.Response

class NewsRepository {

    private val apiService = ApiClient.ArticleApiService()

    suspend fun getViewPagerArticles(): Response<ArticleViewPagerResponse> {
        return apiService.getViewPagerArticles()
    }
}
