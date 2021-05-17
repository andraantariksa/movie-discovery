package com.example.moviediscovery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviediscovery.databinding.FragmentGenresBinding
import com.example.moviediscovery.ui.recyclerview.GenreRecyclerViewAdapter
import com.example.moviediscovery.ui.viewmodel.GenreViewModel

class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private val viewModel: GenreViewModel by lazy {
        ViewModelProvider(
            this,
            GenreViewModel.Factory(requireActivity().application)
        ).get(GenreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenresBinding.inflate(inflater, parent, false)

        setupView(binding)

        return binding.root
    }

    private fun setupView(binding: FragmentGenresBinding) {
        binding.recyclerViewGenres.layoutManager = LinearLayoutManager(context)

        viewModel.isLoadingGenre.observe(viewLifecycleOwner, Observer {
            loading(it)
        })
        viewModel.genres.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewGenres.adapter = GenreRecyclerViewAdapter(it)
            loading(false)
        })
        viewModel.fetchGenre()
    }

    private fun loading(isIt: Boolean) {
        if (isIt) {
            binding.constraintLayoutLoading.visibility = View.VISIBLE
            binding.recyclerViewGenres.visibility = View.GONE
        } else {
            binding.constraintLayoutLoading.visibility = View.GONE
            binding.recyclerViewGenres.visibility = View.VISIBLE
        }
    }
}