package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo

class AddToFavoritesUseCase(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(movieInfo: MovieInfo?) = repositoryMovie.addToFavorites(movieInfo)
}