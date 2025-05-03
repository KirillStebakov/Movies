package com.example.domain.useCases

import com.example.domain.RepositoryMovie

class FetchFavoritesListUseCase(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.fetchFavoritesList()
}