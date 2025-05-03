package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class GetReviewsUseCase(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(movieId: Int) = repositoryMovie.getReviews(movieId)
}