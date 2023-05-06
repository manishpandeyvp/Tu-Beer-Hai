package com.example.tubeerhai.data.dto

import com.example.tubeerhai.data.model.BeerModel

data class BeersListDtoItem(
    val abv: Double,
    val attenuation_level: Double,
    val boil_volume: BoilVolume,
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val ebc: Int,
    val first_brewed: String,
    val food_pairing: List<String>,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val ingredients: Ingredients,
    val method: Method,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    val target_fg: Int,
    val target_og: Double,
    val volume: Volume
) {
    fun toBeerModel(): BeerModel {
        return BeerModel(
            id = id,
            name = name,
            description = description,
            tagline = tagline,
            imageUrl = image_url,
            brewersTips = brewers_tips
        )
    }
}