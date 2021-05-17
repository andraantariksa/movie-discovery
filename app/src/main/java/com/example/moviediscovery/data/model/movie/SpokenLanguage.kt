package com.example.moviediscovery.data.model.movie

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
)