package com.dicoding.mybottomnavtest.api


import com.dicoding.mybottomnavtest.data.EventDetail
import com.dicoding.mybottomnavtest.data.ListEvent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiService {

    @GET("events/{id}")
    fun getEventDetail(@Path("id") eventId: Int): Call<EventDetail>

    @GET("events?active=0")
    fun getFinished(): Call<ListEvent>

    @GET("events?active=1")
    fun getEvents(): Call<ListEvent>
}