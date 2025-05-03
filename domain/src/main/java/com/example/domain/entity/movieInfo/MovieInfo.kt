package com.example.domain.entity.movieInfo

data class MovieInfo(
    val id: Int?,
    val name: String?,
    val year: Int?,
    val description: String?,
    val rating: Rating?,
    val poster: Poster?,
    val trailersList: List<Trailer>?,
)
