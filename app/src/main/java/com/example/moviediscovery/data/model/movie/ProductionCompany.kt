package com.example.moviediscovery.data.model.movie

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)