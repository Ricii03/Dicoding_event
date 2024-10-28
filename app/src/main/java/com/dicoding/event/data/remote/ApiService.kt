package com.dicoding.event.data.remote

import com.dicoding.event.data.response.EventDetailResponse
import com.dicoding.event.data.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvent(
        @Query("active") isActive: Int
    ): EventResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int
    ): EventDetailResponse

    @GET("events")
    suspend fun searchEvent(
        @Query("q") query: String
    ): EventResponse

}