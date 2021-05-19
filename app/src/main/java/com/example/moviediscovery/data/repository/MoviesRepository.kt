package com.example.moviediscovery.data.repository

import com.example.moviediscovery.data.model.movie.Movie
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.model.movie.video.Video
import com.example.moviediscovery.data.model.movie_review.MovieReview
import com.example.moviediscovery.data.network.TheMovieDBService

class MoviesRepository(private val theMovieDBService: TheMovieDBService) {
    suspend fun fetchMovieDetails(id: Int): Movie {
        return theMovieDBService.fetchMovieDetails(id)
    }

    suspend fun fetchMovieVideo(page: Int): Video {
        return theMovieDBService.fetchMovieVideo(page)
    }

    suspend fun fetchDiscoverMovies(page: Int): MovieDiscover {
        return theMovieDBService.fetchDiscoverMovie(page)
    }

    suspend fun <T> fetchDiscoverMoviesWithGenres(genres: Iterable<T>, page: Int): MovieDiscover {
        return theMovieDBService.fetchDiscoverMovieWithGenres(genres.joinToString(","), page)
    }

    suspend fun fetchMovieReview(id: Int, page: Int): MovieReview {
        return theMovieDBService.fetchMovieReviews(id, page)
    }
}