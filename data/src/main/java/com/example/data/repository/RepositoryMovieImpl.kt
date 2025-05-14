package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.data.db.MovieInfoDao
import com.example.data.dto.ApiService
import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RepositoryMovieImpl @Inject constructor(
    private val mapper: MovieMapper,
    private val movieInfoDao: MovieInfoDao,
    private val apiService: ApiService
) : RepositoryMovie {

    private val _moviesState = MutableStateFlow<List<MovieInfo>>(emptyList())
    override val moviesFlow: StateFlow<List<MovieInfo>> = _moviesState.asStateFlow()
    override suspend fun getMoviesList(page: Int) {
        val topMovies = apiService.getTopMovies(page = page)
        val ids = mapper.mapIdListToList(topMovies)
        val newMovies = ids.map { id ->
            apiService.getMoviesInfo(id).let { mapper.mapDToToEntity(it) }
        }
        _moviesState.update { state ->
            (state + newMovies).distinctBy { it.id }
        }
    }

    override fun getMovieInfo(id: Int): MovieInfo? {
        return moviesFlow.value.find { it.id == id }
    }

    private val _reviewsState = MutableStateFlow<List<Review>>(emptyList())
    override val reviewFlow: StateFlow<List<Review>> = _reviewsState.asStateFlow()
    override suspend fun getReviews(movieId: Int, reviewPage: Int) {
        apiService.getReviews(movieId = movieId, page = reviewPage).reviews.let { rawList ->
            if (reviewFlow.value.any { it.movieId != movieId }) _reviewsState.value = emptyList()
            _reviewsState.update { state ->
                (state + mapper.mapReviewListDtoToEntityList(rawList))
                    .distinctBy { it.id }
            }
        }
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