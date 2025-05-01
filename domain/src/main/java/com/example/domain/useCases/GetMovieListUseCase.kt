package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class GetMovieListUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.getMoviesList()
}