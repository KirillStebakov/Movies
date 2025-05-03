package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class RemoveFromFavoritesUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.removeFromFavorites()
}