package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class FetchFavoriteMovieDetailsUseCase(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(id: Int) = repositoryMovie.fetchFavoriteMovieDetails(id)
}