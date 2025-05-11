package com.example.domain

import androidx.lifecycle.LiveData
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review

interface RepositoryMovie {
    suspend fun getMoviesList()
    fun getMovieInfo(id: Int): MovieInfo?
    suspend fun getReviews(movieId: Int)
    suspend fun addToFavorites(movieInfo: MovieInfo?)
    suspend fun removeFromFavorites(movieInfo: MovieInfo?)
    fun fetchFavoritesList(): LiveData<List<MovieInfo?>>
    fun fetchFavoriteMovieDetails(id: Int): LiveData<MovieInfo?>
}