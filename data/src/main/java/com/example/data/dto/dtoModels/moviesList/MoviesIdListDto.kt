package com.example.data.dto.dtoModels.moviesList

import com.google.gson.annotations.SerializedName

data class MoviesIdListDto(
    @SerializedName("docs")
    val movieId: List<MovieIdDto>,
)
