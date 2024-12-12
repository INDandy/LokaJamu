package com.dicoding.mybottomnavtest.api

import com.dicoding.mybottomnavtest.modelresponse.ModelResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiModel {
    @Multipart
    @POST("/api/spices/predict")
    suspend fun getModel(
        @Part image: MultipartBody.Part
    ): Response<ModelResponse>
}
