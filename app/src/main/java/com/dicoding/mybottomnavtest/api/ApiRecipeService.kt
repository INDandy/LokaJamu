package com.dicoding.mybottomnavtest.api

import com.dicoding.mybottomnavtest.Response.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRecipeService {
    @GET("api/recipes")
    suspend fun getRecipes(): Response<RecipeResponse>

    @GET("api/recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): Response<RecipeResponse>

}