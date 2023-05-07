package com.example.tubeerhai.data.repository

import com.example.tubeerhai.data.dto.BeersListDto
import com.example.tubeerhai.data.remote.BeersApiService
import com.example.tubeerhai.domain.repository.MyBeersRepository

class MyBeersRepositoryImpl(
    private val beersApiService: BeersApiService
): MyBeersRepository {
    override suspend fun getAllBears(): BeersListDto {
        return beersApiService.getBeersData()
    }
}