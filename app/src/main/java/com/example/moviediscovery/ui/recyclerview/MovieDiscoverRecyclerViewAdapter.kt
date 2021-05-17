package com.example.moviediscovery.ui.recyclerview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.network.THE_MOVIE_DB_POSTER_URL
import com.example.moviediscovery.databinding.MovieDiscoverItemBinding
import com.example.moviediscovery.ui.activity.MovieDetailsActivity
import com.squareup.picasso.Picasso

class MovieDiscoverRecyclerViewAdapter(
    private val data: MovieDiscover
) : RecyclerView.Adapter<MovieDiscoverRecyclerViewAdapter.Holder>() {

    inner class Holder(val binding: MovieDiscoverItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MovieDiscoverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.textViewTitle.text = data.movieSummaries[position].title
        holder.binding.textViewOverview.text = data.movieSummaries[position].overview
//        Picasso.get().isLoggingEnabled = true
        Picasso
            .get()
            .load(THE_MOVIE_DB_POSTER_URL + "w185" + data.movieSummaries[position].poster_path)
            .into(holder.binding.imageViewThumbnail)
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.binding.root.context, MovieDetailsActivity::class.java)
            intent.putExtra("id", data.movieSummaries[position].id)
            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.movieSummaries.size
    }
}