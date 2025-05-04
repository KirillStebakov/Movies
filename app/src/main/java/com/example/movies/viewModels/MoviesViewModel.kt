package com.example.movies.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryMovieImpl
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.useCases.GetMovieInfoUseCase
import com.example.domain.useCases.GetMovieListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MoviesViewModel() : ViewModel() {
    val repositoryMovie = RepositoryMovieImpl
    val getMovieListUseCase = GetMovieListUseCase(repositoryMovie)
    val getMovieInfoUseCase = GetMovieInfoUseCase(repositoryMovie)

    val movies: Flow<State> = repositoryMovie.moviesFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { emit(State.Loading) }

    fun loadMovieList() {
        viewModelScope.launch {
            getMovieListUseCase()
        }
    }

    init {
        if (repositoryMovie.moviesFlow.value.isEmpty()) {
            loadMovieList()
        }
    }
    fun getMovieInfo(id: Int): MovieInfo? = getMovieInfoUseCase(id)
}