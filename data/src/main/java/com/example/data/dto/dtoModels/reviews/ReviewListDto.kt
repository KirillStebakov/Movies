package com.example.data.dto.dtoModels.reviews

import com.google.gson.annotations.SerializedName

data class ReviewListDto(
    @SerializedName("docs")
    val reviews: List<ReviewDto>
)
