package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class FetchFavoriteMovieDetailsUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.fetchFavoriteMovieDetails()
}