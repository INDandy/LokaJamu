package com.dicoding.mybottomnavtest.api

import com.dicoding.mybottomnavtest.NewsResponse.ArticleDetailResponse
import com.dicoding.mybottomnavtest.NewsResponse.ArticleViewPagerResponse
import com.dicoding.mybottomnavtest.NewsResponse.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiArticle {
    @GET("api/articles/")
    suspend fun getArticles(): Response<NewsResponse>

    @GET("api/articles/")
    suspend fun getViewPagerArticles(): Response<ArticleViewPagerResponse>

    @GET("api/articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDetailResponse>
}