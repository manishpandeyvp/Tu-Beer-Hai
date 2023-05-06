package com.example.tubeerhai.data.remote

import com.example.tubeerhai.data.dto.BeersListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BeersApiService {
    @GET("beers")
    suspend fun getBeersData(): BeersListDto

    @GET("beers/{id}")
    suspend fun getBeersDataWithId(@Path("id")id: String): BeersListDto
}