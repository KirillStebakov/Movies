package com.example.data.dto.dtoModels.movieInfo

import com.google.gson.annotations.SerializedName

data class TrailersListDto(
   @SerializedName("trailers")
   val trailers: List<TrailerDto>
)
