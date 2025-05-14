package com.example.domain

import androidx.lifecycle.LiveData
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review
import kotlinx.coroutines.flow.Flow

interface RepositoryMovie {
    val moviesFlow: Flow<List<MovieInfo>>

    val reviewFlow: Flow<List<Review>>

    suspend fun getMoviesList()

    fun getMovieInfo(id: Int): MovieInfo?

    suspend fun getReviews(movieId: Int)

    suspend fun addToFavorites(movieInfo: MovieInfo?)

    suspend fun removeFromFavorites(movieInfo: MovieInfo?)

    fun fetchFavoritesList(): LiveData<List<MovieInfo?>>

    suspend fun fetchFavoriteMovieDetails(id: Int?): MovieInfo?

    fun checkingIsFavorite(id: Int): LiveData<Boolean>
}