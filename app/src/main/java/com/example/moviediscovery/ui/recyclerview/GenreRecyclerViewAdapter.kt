package com.example.moviediscovery.ui.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviediscovery.R
import com.example.moviediscovery.data.model.genre.Genre
import com.example.moviediscovery.data.model.genre.Genres
import com.example.moviediscovery.databinding.GenreItemBinding

class GenreRecyclerViewAdapter(
    private val data: Genres
) : RecyclerView.Adapter<GenreRecyclerViewAdapter.Holder>() {

    inner class Holder(val binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.textViewGenreName.text = data.genres[position].name
        holder.binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putIntegerArrayList("genres", arrayListOf(data.genres[position].id))
            bundle.putString("name", data.genres[position].name)
            it.findNavController().navigate(R.id.navigation_movies_by_genre, bundle)
        }
    }

    override fun getItemCount(): Int {
        return data.genres.size
    }
}