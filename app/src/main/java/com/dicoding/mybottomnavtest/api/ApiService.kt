package com.dicoding.mybottomnavtest.api

import com.dicoding.finalsubmission1.data.ListEvent
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("events?active=0")
    fun getFinished(): Call<ListEvent>

    @GET("events?active=1")
    fun getEvents(): Call<ListEvent>

}


