package com.example.moviediscovery.data.model.genre

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genres(
    val genres: List<Genre>
)