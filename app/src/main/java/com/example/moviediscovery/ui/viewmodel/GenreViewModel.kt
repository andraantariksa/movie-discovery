package com.example.moviediscovery.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviediscovery.data.model.genre.Genre
import com.example.moviediscovery.data.model.genre.Genres
import com.example.moviediscovery.data.network.TheMovieDBService
import com.example.moviediscovery.data.repository.GenreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GenreViewModel(private val app: Application): ViewModel() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val genreRepository = GenreRepository(TheMovieDBService.invoke(app))

    private val _isLoadingGenre = MutableLiveData<Boolean>(true)
    val isLoadingGenre: LiveData<Boolean> get() = _isLoadingGenre

    private val _genres = MutableLiveData<Genres>()
    val genres: LiveData<Genres> get() = _genres

    fun fetchGenre() {
        ioScope.launch {
            _genres.postValue(genreRepository.fetchGenres())
        }
    }

    class Factory(
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GenreViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GenreViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }

    }
}