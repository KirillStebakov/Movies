package com.example.movies.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryMovieImpl
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.useCases.AddToFavoritesUseCase
import com.example.domain.useCases.CheckingIsFavorite
import com.example.domain.useCases.FetchFavoriteMovieDetailsUseCase
import com.example.domain.useCases.FetchFavoritesListUseCase
import com.example.domain.useCases.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryMovie = RepositoryMovieImpl(application)
    private val fetchFavoritesListUseCase = FetchFavoritesListUseCase(repositoryMovie)
    private val fetchFavoriteMovieDetailsUseCase = FetchFavoriteMovieDetailsUseCase(repositoryMovie)
    private val removeFromFavoritesUseCase = RemoveFromFavoritesUseCase(repositoryMovie)
    private val addToFavoritesUseCase = AddToFavoritesUseCase(repositoryMovie)
    private val isFavoriteUseCase = CheckingIsFavorite(repositoryMovie)

    val favoritesList = fetchFavoritesListUseCase()

    fun isFavorite(id: Int): LiveData<Boolean> = isFavoriteUseCase(id)

    private val _movieDetails = MutableLiveData<MovieInfo?>()
    val movieDetails: LiveData<MovieInfo?> = _movieDetails
    fun fetchDetails(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val details = fetchFavoriteMovieDetailsUseCase(id)
                details?.id?.let{
                    _movieDetails.postValue(details)
                }
            }
        }
    }

    fun addToFavorites(movieInfo: MovieInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addToFavoritesUseCase(movieInfo)
            }
        }
    }

    fun removeFromFavorites(movieInfo: MovieInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                removeFromFavoritesUseCase(movieInfo)
            }
        }
    }
}
