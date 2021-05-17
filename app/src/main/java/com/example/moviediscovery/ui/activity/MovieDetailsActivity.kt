package com.example.moviediscovery.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviediscovery.data.network.GOOGLE_API_KEY
import com.example.moviediscovery.data.network.THE_MOVIE_DB_POSTER_URL
import com.example.moviediscovery.databinding.ActivityMovieDetailsBinding
import com.example.moviediscovery.ui.recyclerview.MovieDetailsVideoRecyclerViewAdapter
import com.example.moviediscovery.ui.viewmodel.MovieViewModel
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.squareup.picasso.Picasso

class MovieDetailsActivity : YouTubeBaseActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieViewModel by lazy {
        ViewModelProvider(
            this,
            MovieViewModel.Factory(application)
        ).get(MovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)

        val movieId = intent.getIntExtra("id", -1)
        setupView(binding, movieId)

        setContentView(binding.root)
    }

    private fun setupView(binding: ActivityMovieDetailsBinding, movieId: Int) {
        viewModel.isLoadingMovie.observe(this, Observer {
            loading(it)
        })
        viewModel.movie.observe(this, Observer {
            binding.textViewMovieTitle.text = it.title
            binding.textViewMovieYear.text = "(${it.release_date.split("-").first()})"
            binding.textViewDuration.text = toStringHourMin(it.runtime)
            binding.textViewMovieReleaseDate.text = it.release_date
            binding.textViewMovieOverview.text = it.overview
            Picasso.get().isLoggingEnabled = true
            Picasso
                .get()
                .load(THE_MOVIE_DB_POSTER_URL + "w185" + it.poster_path)
                .into(binding.imageViewMovieThumbnail)
            Picasso
                .get()
                .load(THE_MOVIE_DB_POSTER_URL + "w500" + it.backdrop_path)
                .into(binding.imageViewBackgroundMovieOverlay)
            loading(false)
        })
        viewModel.fetchMovieDetails(movieId)

        // Videos

        binding.recyclerViewMovieVideosButton.layoutManager =
            LinearLayoutManager(this)

        viewModel.isLoadingMovieVideo.observe(this, Observer {
            if (it) {
                binding.textViewMovieVideoInfo.visibility = View.GONE
                binding.linearLayoutMovieVideosLoading.visibility = View.VISIBLE
                binding.linearLayoutMovieVideosContent.visibility = View.GONE
            } else {
                binding.linearLayoutMovieVideosLoading.visibility = View.GONE
            }
        })
        val youTubePlayerFragment = YouTubePlayerFragment()
        supportFragmentManager
            .beginTransaction()
            .add(binding.frameLayoutYoutubePlayerFragment.id, youTubePlayerFragment)
            .commit()
        viewModel.movieVideo.observe(this, Observer {
            val data = it.results.filter filter@{ video ->
                return@filter video.site == "YouTube"
            }
            if (data.isNotEmpty()) {
                // Open up the first video
                youTubePlayerFragment.initialize(
                    GOOGLE_API_KEY,
                    object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            p0: YouTubePlayer.Provider,
                            youTubePlayer: YouTubePlayer,
                            p2: Boolean
                        ) {
                            binding.recyclerViewMovieVideosButton.adapter =
                                MovieDetailsVideoRecyclerViewAdapter(
                                    data,
                                    youTubePlayer
                                )
                            youTubePlayer.cueVideo(data.first().key)
                        }

                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {
                        }

                    })

                binding.linearLayoutMovieVideosContent.visibility = View.VISIBLE
            } else {
                binding.textViewMovieVideoInfo.visibility = View.VISIBLE
                binding.textViewMovieVideoInfo.text = "No video found"
            }
        })
        viewModel.fetchMovieVideo(movieId)
    }

    private fun toStringHourMin(time: Int): String {
        var s = ""
        if (time >= 60) {
            s += "${time / 60}h"
        }
        if (time > 0) {
            if (s.isNotEmpty()) {
                s += " "
            }
            s += "${time % 60}m"
        }
        return s
    }

    private fun loading(isIt: Boolean) {
        if (isIt) {
            binding.constraintLayoutLoading.visibility = View.VISIBLE
            binding.constraintLayoutMain.visibility = View.GONE
        } else {
            binding.constraintLayoutLoading.visibility = View.GONE
            binding.constraintLayoutMain.visibility = View.VISIBLE
        }
    }
}