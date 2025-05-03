package com.example.domain.entity.reviews

data class Review(
    val id: Int?,
    val movieId: Int?,
    val type: String?,
    val review: String?,
    val date: String?,
    val author: String?,
)