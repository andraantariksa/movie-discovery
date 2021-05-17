package com.example.moviediscovery.data.model.movie.video

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Video(
    val id: Int,
    val results: List<Result>
)