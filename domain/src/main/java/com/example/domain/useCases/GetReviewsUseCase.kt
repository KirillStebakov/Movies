package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke(movieId: Int) = repositoryMovie.getReviews(movieId)
}