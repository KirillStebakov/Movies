package com.example.domain

import androidx.lifecycle.LiveData
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review

interface RepositoryMovie {
   suspend fun getMoviesList()
    fun getMovieInfo(id: Int): MovieInfo?
    suspend fun getReviews(movieId: Int): List<Review>
    fun addToFavorites()
    fun removeFromFavorites()
    fun fetchFavoritesList(): LiveData<List<MovieInfo>>
    fun fetchFavoriteMovieDetails(): LiveData<MovieInfo>
    fun fetchReviews(movieId: Int): LiveData<List<Review>>
}