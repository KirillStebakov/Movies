package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class CheckingIsFavorite(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke(id: Int) = repositoryMovie.checkingIsFavorite(id)
}