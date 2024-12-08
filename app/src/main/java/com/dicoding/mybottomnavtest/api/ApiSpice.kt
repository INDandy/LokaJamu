package com.dicoding.mybottomnavtest.api

import com.dicoding.mybottomnavtest.Response.ApiResponse
import com.dicoding.mybottomnavtest.Response.Spice
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiSpice {
    @GET("api/spices/")
    suspend fun getSpice(): Response<ApiResponse>

    @GET("api/spices/{id}")
    suspend fun getSpiceById(@Path("id") id: Int): Response<Spice>

}