package com.example.data.dto.dtoModels.movieInfo

import com.google.gson.annotations.SerializedName

data class MovieInfoDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("rating")
    val rating: RatingDto?,
    @SerializedName("poster")
    val poster: PosterDto?,
    @SerializedName("videos")
    val trailersList: TrailersListDto?,
)
