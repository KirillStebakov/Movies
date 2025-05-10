package com.example.movies.adapters.adapterReviews

import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.reviews.Review
import com.example.movies.databinding.ItemReviewBinding

class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Review, @ColorInt backgroundColor: Int) {
        binding.root.setBackgroundColor(backgroundColor)
        binding.authorName.text = data.author
        binding.reviewText.text = data.review
    }
}