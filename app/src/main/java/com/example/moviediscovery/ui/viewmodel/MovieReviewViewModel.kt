package com.example.moviediscovery.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.model.movie_review.MovieReview
import com.example.moviediscovery.data.network.TheMovieDBService
import com.example.moviediscovery.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieReviewViewModel(private val app: Application): ViewModel() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val moviesRepository = MoviesRepository(TheMovieDBService.invoke(app))

    private val _isLoadingMovieReview = MutableLiveData<Boolean>(true)
    val isLoadingMovieReview: LiveData<Boolean> get() = _isLoadingMovieReview

    private val _movieReview = MutableLiveData<MovieReview>()
    val movieReview: LiveData<MovieReview> get() = _movieReview

    fun fetchMovieReview(movieId: Int, page: Int) {
        ioScope.launch {
            _movieReview.postValue(moviesRepository.fetchMovieReview(movieId, page))
        }
    }

    class Factory(
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieReviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieReviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }

    }
}