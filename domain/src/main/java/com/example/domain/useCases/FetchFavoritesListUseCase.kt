package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import javax.inject.Inject

class FetchFavoritesListUseCase @Inject constructor(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke() = repositoryMovie.fetchFavoritesList()
}