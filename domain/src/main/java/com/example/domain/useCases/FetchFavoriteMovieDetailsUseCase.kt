package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import javax.inject.Inject

class FetchFavoriteMovieDetailsUseCase @Inject constructor(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(id: Int?) = repositoryMovie.fetchFavoriteMovieDetails(id)
}