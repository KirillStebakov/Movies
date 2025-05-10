package com.example.movies.adapters.adapterMovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.movies.R
import com.example.movies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieInfoAdapter() :
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
                tvRating.text = String.format("%.1f".format(rating?.kp))
                val color = when {
                    (rating?.kp ?: 0.0) <= 4.0 -> R.color.red
                    (rating?.kp ?: 0.0) <= 7 -> R.color.yellow
                    else -> R.color.green
                }
                tvRating.setRoundedBackgroundColor(color)

                poster?.url?.let { Picasso.get().load(it).into(ivPoster) }
                    ?: ivPoster.setImageResource(R.drawable.empty_poster)

                root.setOnClickListener {
                    onMovieClickListener?.invoke(this)
                }
            }
        }
    }
}