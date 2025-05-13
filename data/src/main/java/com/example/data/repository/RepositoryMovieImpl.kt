package com.example.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.data.db.AppDatabase
import com.example.data.dto.ApiFactory.apiService
import com.example.data.dto.dtoModels.movieInfo.MovieInfoDto
import com.example.data.dto.dtoModels.reviews.ReviewDto
import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepositoryMovieImpl(application: Application) : RepositoryMovie {
    private val mapper = MovieMapper()
    private val movieInfoDao = AppDatabase.getInstance(application).movieInfoDao()

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

    private var currentReviewPage = 0
    private val cachedReview = mutableListOf<ReviewDto>()
    private val _reviewsState = MutableStateFlow<List<Review>>(emptyList())
    val reviewFlow: StateFlow<List<Review>> = _reviewsState.asStateFlow()
    override suspend fun getReviews(movieId: Int) {
        val nextPage = currentReviewPage + 1
        apiService.getReviews(movieId = movieId, page = nextPage).reviews.forEach {
            cachedReview.add(it)
        }
        currentReviewPage = nextPage
        _reviewsState.value = cachedReview.map { mapper.mapReviewDtoToEntity(it) }
    }

    override suspend fun addToFavorites(movie: MovieInfo?) {
        movieInfoDao.insertMovieToFavorite(mapper.mapEntityToDbModel(movie))
    }

    override suspend fun removeFromFavorites(movie: MovieInfo?) {
        movieInfoDao.deleteMovieFromFavorite(movie?.id)
    }

    override fun fetchFavoritesList(): LiveData<List<MovieInfo?>> {
        return movieInfoDao.getFavoriteMovieList().map { mapper.mapListDbModelToListEntity(it) }
    }

    override suspend fun fetchFavoriteMovieDetails(id: Int?): MovieInfo? {
        return movieInfoDao.getFavoriteMovie(id).let { mapper.mapDbModelToEntity(it) }
    }

    override fun checkingIsFavorite(id: Int): LiveData<Boolean> {
        return movieInfoDao.isFavorite(id)
    }
}