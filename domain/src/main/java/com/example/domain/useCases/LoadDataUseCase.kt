package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class LoadDataUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.getMovieInfo()
}