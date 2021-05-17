package com.example.moviediscovery.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviediscovery.data.model.movie.Movie
import com.example.moviediscovery.data.model.movie.video.Video
import com.example.moviediscovery.data.network.TheMovieDBService
import com.example.moviediscovery.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieViewModel(private val app: Application): ViewModel() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val moviesRepository = MoviesRepository(TheMovieDBService.invoke(app))

    private val _isLoadingMovie = MutableLiveData<Boolean>(true)
    val isLoadingMovie: LiveData<Boolean> get() = _isLoadingMovie

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    fun fetchMovieDetails(id: Int) {
        ioScope.launch {
            _movie.postValue(moviesRepository.fetchMovieDetails(id))
        }
    }

    private val _isLoadingMovieVideo = MutableLiveData<Boolean>(true)
    val isLoadingMovieVideo: LiveData<Boolean> get() = _isLoadingMovieVideo

    private val _movieVideo = MutableLiveData<Video>()
    val movieVideo: LiveData<Video> get() = _movieVideo

    fun fetchMovieVideo(id: Int) {
        ioScope.launch {
            _movieVideo.postValue(moviesRepository.fetchMovieVideo(id))
        }
    }

    class Factory(
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }

    }
}