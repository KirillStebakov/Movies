package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(movieInfo: MovieInfo?) = repositoryMovie.addToFavorites(movieInfo)
}