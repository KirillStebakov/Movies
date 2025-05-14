package com.example.movies.adapters.adapterTrailers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.movieInfo.Trailer
import com.example.movies.databinding.ItemTrailersBinding

class TrailersAdapter() :
    ListAdapter<Trailer, TrailersViewHolder>(TrailersDiffCallback()) {

    var onTrailerClickListener: ((Trailer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {
        val binding =
            ItemTrailersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TrailersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            with(movie) {
                tvTrailerName.text = name
                root.setOnClickListener {
                    onTrailerClickListener?.invoke(this)
                }
            }
        }
    }
}