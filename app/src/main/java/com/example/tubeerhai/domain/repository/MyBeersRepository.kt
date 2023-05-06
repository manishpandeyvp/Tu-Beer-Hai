package com.example.tubeerhai.domain.repository

import com.example.tubeerhai.data.dto.BeersListDto
import com.example.tubeerhai.data.dto.BeersListDtoItem

interface MyBeersRepository {
    suspend fun getAllBears(): BeersListDto
    suspend fun getBeerWithId(id: String): BeersListDtoItem?
}