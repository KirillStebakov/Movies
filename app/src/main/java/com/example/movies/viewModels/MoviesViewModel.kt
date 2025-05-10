package com.example.movies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryMovieImpl
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review
import com.example.domain.useCases.GetMovieInfoUseCase
import com.example.domain.useCases.GetMovieListUseCase
import com.example.domain.useCases.GetReviewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel() : ViewModel() {
    val repositoryMovie = RepositoryMovieImpl
    val getMovieListUseCase = GetMovieListUseCase(repositoryMovie)
    val getMovieInfoUseCase = GetMovieInfoUseCase(repositoryMovie)
    val getReviewsUseCase = GetReviewsUseCase(repositoryMovie)

    val movies: Flow<State> = repositoryMovie.moviesFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { emit(State.Loading) }

    private val _isMoviesInvoked = MutableLiveData<Boolean>(true)
    val isMoviesInvoked = _isMoviesInvoked as LiveData<Boolean>
    fun loadMovieList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getMovieListUseCase()
                _isMoviesInvoked.postValue(false)
            }
        }
    }

    fun getMovieInfo(id: Int): MovieInfo? = getMovieInfoUseCase(id)

    private val _isReviewInvoked = MutableLiveData<Boolean>(true)
    val isReviewInvoked = _isReviewInvoked as LiveData<Boolean>

    fun loadReviewList(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getReviewsUseCase(movieId)
                _isReviewInvoked.postValue(false)
            }
        }
    }

    val reviews: Flow<List<Review>> = repositoryMovie.reviewFlow
}