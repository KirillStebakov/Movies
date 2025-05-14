package com.example.domain.useCases

import com.example.domain.RepositoryMovie
import javax.inject.Inject

class CheckingIsFavoriteUseCase @Inject constructor(private val repositoryMovie: RepositoryMovie) {
    operator fun invoke(id: Int) = repositoryMovie.checkingIsFavorite(id)
}