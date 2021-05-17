package com.example.moviediscovery.data.model.movie

import com.example.moviediscovery.data.model.movie.MovieSummary
import com.squareup.moshi.Json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDiscover(
    var page: Int,
    @field:Json(name = "results") val movieSummaries: MutableList<MovieSummary>,
    var total_pages: Int,
    var total_results: Int
)