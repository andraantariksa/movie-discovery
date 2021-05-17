package com.example.moviediscovery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviediscovery.data.model.movie.Movie
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.model.movie.MovieSummary
import com.example.moviediscovery.databinding.FragmentMoviesByGenreBinding
import com.example.moviediscovery.ui.activity.MainActivity
import com.example.moviediscovery.ui.recyclerview.MovieDiscoverRecyclerViewAdapter
import com.example.moviediscovery.ui.viewmodel.MovieDiscoverViewModel

class MoviesByGenreFragment : Fragment() {

    private lateinit var binding: FragmentMoviesByGenreBinding
    private val viewModel: MovieDiscoverViewModel by lazy {
        ViewModelProvider(
            this,
            MovieDiscoverViewModel.Factory(requireActivity().application)
        ).get(MovieDiscoverViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val genres = requireArguments().getIntegerArrayList("genres")!!

        binding = FragmentMoviesByGenreBinding.inflate(inflater, parent, false)

        setupView(binding, genres)

        return binding.root
    }

    private fun <T> setupView(binding: FragmentMoviesByGenreBinding, genres: Iterable<T>) {
        val recyclerViewMoviesLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewMovies.layoutManager = recyclerViewMoviesLayoutManager
        binding.recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var page = 1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Scroll down
                if (dy > 0) {
                    val visibleItemCount = recyclerViewMoviesLayoutManager.getChildCount();
                    val totalItemCount = recyclerViewMoviesLayoutManager.getItemCount();
                    val pastVisiblesItems =
                        recyclerViewMoviesLayoutManager.findFirstVisibleItemPosition();

                    if (viewModel.isLoadingMovies.value!!) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading(true)
                            page += 1
                            viewModel.fetchDiscoverMoviesWithGenres(genres, page)
                        }
                    }
                }
            }
        })
        viewModel.isLoadingMovies.observe(viewLifecycleOwner, Observer {
            loading(it)
        })
        val movieSummaries = mutableListOf<MovieSummary>()
        val movieDiscover = MovieDiscover(1, movieSummaries, 0, 0)
        val recyclerViewMoviesAdapter = MovieDiscoverRecyclerViewAdapter(movieDiscover)
        binding.recyclerViewMovies.adapter = recyclerViewMoviesAdapter
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            movieSummaries.addAll(it.movieSummaries)
            movieDiscover.page = it.page
            movieDiscover.total_pages = it.total_pages
            movieDiscover.total_results = it.total_results
            recyclerViewMoviesAdapter.notifyDataSetChanged()
            loading(false)
        })
        viewModel.fetchDiscoverMoviesWithGenres(genres, 1)
    }

    private fun loading(isIt: Boolean) {
//        TODO remove constraintlayout loading?
        if (isIt) {
//            binding.constraintLayoutLoading.visibility = View.VISIBLE
            binding.linearLayoutRecyclerViewMoviesLoading.visibility = View.VISIBLE
//            binding.recyclerViewMovies.visibility = View.GONE
        } else {
            binding.linearLayoutRecyclerViewMoviesLoading.visibility = View.GONE
//            binding.constraintLayoutLoading.visibility = View.GONE
//            binding.recyclerViewMovies.visibility = View.VISIBLE
        }
    }
}