package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val repositoryMovie: RepositoryMovie) {
    suspend operator fun invoke() = repositoryMovie.getMoviesList()
}