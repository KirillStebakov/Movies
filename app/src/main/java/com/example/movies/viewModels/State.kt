package com.example.movies.viewModels

import com.example.domain.entity.movieInfo.MovieInfo

sealed class State {
    object Initial : State()
    object Loading : State()
    data class Content(val movieList: List<MovieInfo>) : State()
}