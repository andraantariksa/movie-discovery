package com.example.moviediscovery.ui.recyclerview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.moviediscovery.R
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.model.movie.video.Result
import com.example.moviediscovery.data.network.GOOGLE_API_KEY
import com.example.moviediscovery.data.network.THE_MOVIE_DB_POSTER_URL
import com.example.moviediscovery.databinding.MovieDetailsVideosItemBinding
import com.example.moviediscovery.databinding.MovieDiscoverItemBinding
import com.example.moviediscovery.ui.activity.MovieDetailsActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso

class MovieDetailsVideoRecyclerViewAdapter(
    private val data: List<Result>,
    private val youtubePlayer: YouTubePlayer?
) : RecyclerView.Adapter<MovieDetailsVideoRecyclerViewAdapter.Holder>() {

    inner class Holder(val binding: MovieDetailsVideosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MovieDetailsVideosItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.root.text = data[position].name
        holder.binding.root.setOnClickListener {
            youtubePlayer?.cueVideo(data[position].key)
            if (youtubePlayer != null) {
                Log.d("aaaaa", "ok")
            } else {
                Log.d("aaaaa", "not ok")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}