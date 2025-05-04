package com.example.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.movies.R
import com.example.movies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieInfoAdapter(private val context: Context) :
    ListAdapter<MovieInfo, MovieInfoViewHolder>(MovieInfoDiffCallback()) {

    var onMovieClickListener: ((MovieInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieInfoViewHolder {
        val binding =
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MovieInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieInfoViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            with(movie) {
                val ratingTemplate = context.resources.getString(R.string.movie_main_activity_rating)
                val nameTemplate = context.resources.getString(R.string.movie_main_activity_name)
                tvRating.text = String.format(ratingTemplate, "%.1f".format(rating?.kp))
                tvName.text = String.format(nameTemplate, name)
                Picasso.get().load(poster?.url).into(ivPoster)
                root.setOnClickListener {
                    onMovieClickListener?.invoke(movie)
                }
            }
        }
    }
}