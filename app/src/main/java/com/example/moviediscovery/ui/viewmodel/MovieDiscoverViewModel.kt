package com.example.moviediscovery.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviediscovery.data.model.genre.Genres
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.network.TheMovieDBService
import com.example.moviediscovery.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDiscoverViewModel(private val app: Application): ViewModel() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val moviesRepository = MoviesRepository(TheMovieDBService.invoke(app))

    private val _isLoadingMovies = MutableLiveData<Boolean>(true)
    val isLoadingMovies: LiveData<Boolean> get() = _isLoadingMovies

    private val _movies = MutableLiveData<MovieDiscover>()
    val movies: LiveData<MovieDiscover> get() = _movies

    fun fetchDiscoverMovies(page: Int) {
        ioScope.launch {
            _movies.postValue(moviesRepository.fetchDiscoverMovies(page))
        }
    }

    fun <T> fetchDiscoverMoviesWithGenres(genres: Iterable<T>, page: Int) {
        ioScope.launch {
            _movies.postValue(moviesRepository.fetchDiscoverMoviesWithGenres(genres, page))
        }
    }

    class Factory(
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDiscoverViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieDiscoverViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }

    }
}