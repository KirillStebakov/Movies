package com.example.domain

import androidx.lifecycle.LiveData
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review

interface RepositoryMovie {
    fun getMoviesList(): LiveData<List<MovieInfo>>
    fun getMovieInfo(): LiveData<MovieInfo>
    fun getReviews(): LiveData<List<Review>>
    fun loadData()
}