package com.example.data.dto.dtoModels.movieInfo

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("url")
    val url: String?,
    @SerializedName("previewUrl")
    val previewUrl: String?
)
