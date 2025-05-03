package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class FetchReviewsUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke(movieId: Int) = repositoryMovie.fetchReviews(movieId)
}