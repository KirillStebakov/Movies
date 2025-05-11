package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo

class RemoveFromFavoritesUseCase(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(movieInfo: MovieInfo?) = repositoryMovie.removeFromFavorites(movieInfo)
}