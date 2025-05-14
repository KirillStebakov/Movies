package com.example.movies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review
import com.example.domain.useCases.AddToFavoritesUseCase
import com.example.domain.useCases.CheckingIsFavoriteUseCase
import com.example.domain.useCases.GetMovieInfoUseCase
import com.example.domain.useCases.GetMovieListUseCase
import com.example.domain.useCases.GetReviewsUseCase
import com.example.domain.useCases.RemoveFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    repositoryMovie: RepositoryMovie,
    private val getMovieListUseCase: GetMovieListUseCase,
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCase: CheckingIsFavoriteUseCase,
) : ViewModel() {

    val movies: Flow<State> = repositoryMovie.moviesFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { emit(State.Loading) }

    private var page = 1
    private val _isMoviesInvoked = MutableLiveData<Boolean>(true)
    val isMoviesInvoked = _isMoviesInvoked as LiveData<Boolean>
    fun loadMovieList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getMovieListUseCase(page++)
                _isMoviesInvoked.postValue(false)
            }
        }
    }

    fun getMovieInfo(id: Int): MovieInfo? = getMovieInfoUseCase(id)

    fun loadReviewList(movieId: Int, page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getReviewsUseCase(movieId, page)
            }
        }
    }

    val reviews: Flow<List<Review>> = repositoryMovie.reviewFlow

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

    fun isFavorite(id: Int) = isFavoriteUseCase(id)
}