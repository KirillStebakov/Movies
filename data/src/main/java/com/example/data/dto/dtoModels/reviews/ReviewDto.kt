package com.example.data.dto.dtoModels.reviews

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("movieId")
    val movieId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("author")
    val author: String,
)
