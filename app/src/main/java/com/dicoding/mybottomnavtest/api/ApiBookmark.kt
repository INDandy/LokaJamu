package com.dicoding.mybottomnavtest.api

import com.dicoding.mybottomnavtest.NewsResponse.NewsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiBookmark {
    @POST("api/users/{id}/bookmark-articles")
    suspend fun bookmarkArticle(
        @Path("id") userId: String,
        @Body articleId: Map<String, Int>
    ): Response<NewsResponse>
}