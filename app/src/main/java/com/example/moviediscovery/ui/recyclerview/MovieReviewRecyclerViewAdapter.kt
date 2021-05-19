package com.example.moviediscovery.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviediscovery.data.model.movie_review.MovieReview
import com.example.moviediscovery.data.network.THE_MOVIE_DB_POSTER_URL
import com.example.moviediscovery.databinding.MovieDetailsReviewItemBinding
import com.squareup.picasso.Picasso

class MovieReviewRecyclerViewAdapter(
    private val data: MovieReview
) : RecyclerView.Adapter<MovieReviewRecyclerViewAdapter.Holder>() {

    inner class Holder(val binding: MovieDetailsReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MovieDetailsReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.textViewComment.text = data.results[position].content
        holder.binding.textViewName.text = data.results[position].author
        holder.binding.textViewDatetime.text = data.results[position].updatedAt
        data.results[position].authorDetails.avatarPath?.let {
            Picasso
                .get()
                .load(THE_MOVIE_DB_POSTER_URL + "w185" + it)
                .into(holder.binding.imageViewAvatar)
        }
    }

    override fun getItemCount(): Int {
        return data.results.size
    }
}