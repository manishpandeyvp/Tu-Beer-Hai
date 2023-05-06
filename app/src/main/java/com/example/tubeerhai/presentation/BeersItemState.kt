package com.example.tubeerhai.presentation

import com.example.tubeerhai.data.model.BeerModel

data class BeersItemState (
    val isLoading: Boolean = false,
    val beer: BeerModel? = null,
    val error: String = ""
)