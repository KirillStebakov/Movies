package com.example.movies.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryMovieImpl
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.useCases.AddToFavoritesUseCase
import com.example.domain.useCases.FetchFavoriteMovieDetailsUseCase
import com.example.domain.useCases.FetchFavoritesListUseCase
import com.example.domain.useCases.RemoveFromFavoritesUseCase
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryMovie = RepositoryMovieImpl(application)
    private val fetchFavoritesListUseCase = FetchFavoritesListUseCase(repositoryMovie)
    private val fetchFavoriteMovieDetailsUseCase = FetchFavoriteMovieDetailsUseCase(repositoryMovie)
    private val removeFromFavoritesUseCase = RemoveFromFavoritesUseCase(repositoryMovie)
    private val addToFavoritesUseCase = AddToFavoritesUseCase(repositoryMovie)

    val favoritesList = fetchFavoritesListUseCase()
    fun favoriteMovieDetails(id: Int) = fetchFavoriteMovieDetailsUseCase(id)

    fun addToFavorites(movieInfo: MovieInfo?) {
        viewModelScope.launch {
            addToFavoritesUseCase(movieInfo)
        }
    }
    fun removeFromFavorites(movieInfo: MovieInfo?) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(movieInfo)
        }
    }
}