package com.example.data.dto.dtoModels.movieInfo

import com.google.gson.annotations.SerializedName

data class MoviesListDto(
    @SerializedName("docs")
    val movieInfos: List<MovieInfoDto>
)
