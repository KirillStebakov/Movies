package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.dto.ApiFactory
import com.example.data.dto.ApiFactory.apiService
import com.example.data.dto.dtoModels.movieInfo.MovieInfoDto
import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object RepositoryMovieImpl : RepositoryMovie {
    val apiServiceImpl = ApiFactory.apiService
    val mapper = MovieMapper()
    private var currentPage = 0
    private val cachedData = mutableListOf<MovieInfoDto>()
    private val _moviesState = MutableStateFlow<List<MovieInfo>>(emptyList())
    val moviesFlow: StateFlow<List<MovieInfo>> = _moviesState.asStateFlow()
    override suspend fun getMoviesList() {
        val nextPage = currentPage + 1
        val topMovies = apiService.getTopMovies(page = nextPage)
        val ids = mapper.mapIdListToList(topMovies)
        ids.forEach { id ->
            if (cachedData.none { it.id == id }) {
                val movieInfoDto = apiService.getMoviesInfo(id)
                cachedData.add(movieInfoDto)
            }
        }
        currentPage = nextPage
        _moviesState.value = cachedData.map { mapper.mapDToToEntity(it) }
    }

    override fun getMovieInfo(id: Int): MovieInfo? {
        return cachedData.find { it.id == id }?.let { mapper.mapDToToEntity(it) }
    }

    override suspend fun getReviews(movieId: Int): List<Review> {
        val reviews = apiServiceImpl.getReviews(movieId = movieId).reviews.map {
            mapper.mapReviewDtoToEntity(it)
        }
        return reviews
    }

    override fun addToFavorites() {
        TODO("Not yet implemented")
    }

    override fun removeFromFavorites() {
        TODO("Not yet implemented")
    }

    override fun fetchFavoritesList(): LiveData<List<MovieInfo>> {
        TODO("Not yet implemented")
    }

    override fun fetchFavoriteMovieDetails(): LiveData<MovieInfo> {
        TODO("Not yet implemented")
    }

    override fun fetchReviews(movieId: Int): LiveData<List<Review>> {
        TODO("Not yet implemented")
    }
}