package com.example.tubeerhai.presentation

import com.example.tubeerhai.data.model.BeerModel

data class BeersListState (
    val isLoading: Boolean = false,
    val beersList: List<BeerModel> = emptyList(),
    val error: String = ""
)