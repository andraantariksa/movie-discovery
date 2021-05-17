package com.example.moviediscovery.data.model.movie

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)