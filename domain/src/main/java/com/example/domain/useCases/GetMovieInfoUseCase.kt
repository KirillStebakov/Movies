package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class GetMovieInfoUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke(id: Int) = repositoryMovie.getMovieInfo(id)
}