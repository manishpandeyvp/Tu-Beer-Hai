package com.example.tubeerhai.domain.repository

import com.example.tubeerhai.data.dto.BeersListDto

interface MyBeersRepository {
    suspend fun getAllBears(): BeersListDto
}