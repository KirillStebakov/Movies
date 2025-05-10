package com.example.movies.adapters.adapterTrailers

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.movieInfo.Trailer

class TrailersDiffCallback : DiffUtil.ItemCallback<Trailer>() {
    override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
        return oldItem == newItem
    }
}