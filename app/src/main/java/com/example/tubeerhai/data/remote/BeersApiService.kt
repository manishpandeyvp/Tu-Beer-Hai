package com.example.tubeerhai.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface BeersApiService {
    @GET("/beers")
    suspend fun getBeersData()

    @GET("/beers/{id}")
    suspend fun getBeersDataWithId(@Path("id")id: String)
}