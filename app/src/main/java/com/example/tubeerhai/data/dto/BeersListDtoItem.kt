package com.example.tubeerhai.data.dto

import com.example.tubeerhai.data.model.BeerModel

data class BeersListDtoItem(
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val first_brewed: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val tagline: String
) {
    fun toBeerModel(): BeerModel {
        return BeerModel(
            id = id,
            name = name,
            description = description,
            tagline = tagline,
            imageUrl = image_url,
            brewersTips = brewers_tips,
            firstBrewed = first_brewed
        )
    }
}