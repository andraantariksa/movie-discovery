package com.example.moviediscovery.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviediscovery.R
import com.example.moviediscovery.databinding.ActivityMovieDetailsReviewBinding
import com.example.moviediscovery.ui.recyclerview.MovieReviewRecyclerViewAdapter
import com.example.moviediscovery.ui.viewmodel.MovieReviewViewModel
import com.example.moviediscovery.ui.viewmodel.MovieViewModel

class MovieDetailsReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsReviewBinding
    private var maxPage = 1
    private val viewModel: MovieReviewViewModel by lazy {
        ViewModelProvider(
            this,
            MovieReviewViewModel.Factory(application)
        ).get(MovieReviewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsReviewBinding.inflate(layoutInflater)

        val movieId = intent.getIntExtra("movieId", -1)
        setupView(movieId)

        setContentView(binding.root)
    }

    private fun setupView(movieId: Int) {
        val recyclerViewMoviesLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewReview.layoutManager = recyclerViewMoviesLayoutManager
        binding.recyclerViewReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var page = 1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Scroll down
                if (dy > 0) {
                    val visibleItemCount = recyclerViewMoviesLayoutManager.getChildCount();
                    val totalItemCount = recyclerViewMoviesLayoutManager.getItemCount();
                    val pastVisiblesItems =
                        recyclerViewMoviesLayoutManager.findFirstVisibleItemPosition();

                    if (viewModel.isLoadingMovieReview.value!!) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (page < maxPage) {
                                loading(true)
                                page += 1
                                viewModel.fetchMovieReview(movieId, page)
                            }
                        }
                    }
                }
            }
        })

        viewModel.isLoadingMovieReview.observe(this, Observer {
            loading(it)
        })
        viewModel.movieReview.observe(this, Observer {
            maxPage = it.totalPages
            binding.recyclerViewReview.adapter = MovieReviewRecyclerViewAdapter(it)
            loading(false)
        })
        viewModel.fetchMovieReview(movieId, 1)
    }

    private fun loading(isIt: Boolean) {
        // TODO
    }
}