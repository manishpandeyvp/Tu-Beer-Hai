package com.example.tubeerhai.data.remote

import com.example.tubeerhai.data.dto.BeersListDto
import retrofit2.http.GET

interface BeersApiService {
    @GET("beers")
    suspend fun getBeersData(): BeersListDto
}