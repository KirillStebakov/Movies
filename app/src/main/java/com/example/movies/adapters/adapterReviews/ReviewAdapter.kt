package com.example.movies.adapters.adapterReviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.reviews.Review
import com.example.movies.databinding.ItemReviewBinding

class ReviewAdapter() :
    ListAdapter<Review, ReviewViewHolder>(ReviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val data = getItem(position)
        val backgroundColor = when (data.type) {
            POSITIVE -> ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.holo_green_light
            )

            NEGATIVE -> ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.holo_red_light
            )

            NEUTRAL -> ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.holo_orange_light
            )

            else -> throw RuntimeException("Unknown type: ${data.type}")
        }
        holder.bind(data, backgroundColor)
    }

    companion object {
        const val POSITIVE = "Позитивный"
        const val NEGATIVE = "Негативный"
        const val NEUTRAL = "Нейтральный"
    }
}