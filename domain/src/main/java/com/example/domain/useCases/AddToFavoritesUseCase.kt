package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class AddToFavoritesUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.addToFavorites()
}