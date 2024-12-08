package com.dicoding.mybottomnavtest.api

import com.dicoding.mybottomnavtest.Response.GetUsers
import com.dicoding.mybottomnavtest.Response.LoginRequest
import com.dicoding.mybottomnavtest.Response.LoginResponseSuccess
import com.dicoding.mybottomnavtest.Response.RegisterRequest
import com.dicoding.mybottomnavtest.Response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/api/users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/api/users/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponseSuccess

    @GET("api/users/current")
    suspend fun getUserDetails(
        @Header("Authorization") token: String)
    : Response<GetUsers>




}


