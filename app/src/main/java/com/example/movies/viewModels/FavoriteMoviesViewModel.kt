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
    private val _isInvoked = MutableLiveData<Boolean>(true)
    val isInvoked: LiveData<Boolean> = _isInvoked
    fun fetchDetails(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val details = fetchFavoriteMovieDetailsUseCase(id)
                _movieDetails.postValue(details)
                _isInvoked.postValue(false)
            }
        }
    }

    fun addToFavorites(movieInfo: MovieInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                addToFavoritesUseCase(movieInfo)
            }
        }
    }

    fun removeFromFavorites(movieInfo: MovieInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                removeFromFavoritesUseCase(movieInfo)
            }
        }
    }
}
