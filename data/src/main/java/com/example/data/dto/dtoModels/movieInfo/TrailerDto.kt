package com.example.data.dto.dtoModels.movieInfo

import com.google.gson.annotations.SerializedName

data class TrailerDto(
     @SerializedName("url")
     val url: String,
     @SerializedName("name")
     val name: String,
     @SerializedName("site")
     val site: String,
     @SerializedName("size")
     val size: Int,
     @SerializedName("type")
     val type: String
)
