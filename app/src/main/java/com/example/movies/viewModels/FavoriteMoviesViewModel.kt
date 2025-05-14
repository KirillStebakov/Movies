package com.example.movies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.useCases.AddToFavoritesUseCase
import com.example.domain.useCases.CheckingIsFavoriteUseCase
import com.example.domain.useCases.FetchFavoriteMovieDetailsUseCase
import com.example.domain.useCases.FetchFavoritesListUseCase
import com.example.domain.useCases.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteMoviesViewModel @Inject constructor(
    fetchFavoritesListUseCase: FetchFavoritesListUseCase,
    private val fetchFavoriteMovieDetailsUseCase: FetchFavoriteMovieDetailsUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val isFavoriteUseCase: CheckingIsFavoriteUseCase,
) : ViewModel() {


    val favoritesList = fetchFavoritesListUseCase()

    fun isFavorite(id: Int): LiveData<Boolean> = isFavoriteUseCase(id)

    private val _movieDetails = MutableLiveData<MovieInfo?>()
    val movieDetails: LiveData<MovieInfo?> = _movieDetails
    fun fetchDetails(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val details = fetchFavoriteMovieDetailsUseCase(id)
                details?.id?.let {
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
