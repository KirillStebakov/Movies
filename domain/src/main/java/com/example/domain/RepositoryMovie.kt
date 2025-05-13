package com.example.domain

import androidx.lifecycle.LiveData
import com.example.domain.entity.movieInfo.MovieInfo

interface RepositoryMovie {
    suspend fun getMoviesList()
    fun getMovieInfo(id: Int): MovieInfo?
    suspend fun getReviews(movieId: Int)
    suspend fun addToFavorites(movieInfo: MovieInfo?)
    suspend fun removeFromFavorites(movieInfo: MovieInfo?)
    fun fetchFavoritesList(): LiveData<List<MovieInfo?>>
    suspend fun fetchFavoriteMovieDetails(id: Int?): MovieInfo?
    fun checkingIsFavorite(id: Int): LiveData<Boolean>
}