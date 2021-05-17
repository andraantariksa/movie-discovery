package com.example.moviediscovery.data.repository

import android.content.Context
import com.example.moviediscovery.data.model.genre.Genres
import com.example.moviediscovery.data.network.TheMovieDBService

class GenreRepository(private val theMovieDBService: TheMovieDBService) {
    suspend fun fetchGenres(): Genres {
        return theMovieDBService.fetchGenres()
    }
}