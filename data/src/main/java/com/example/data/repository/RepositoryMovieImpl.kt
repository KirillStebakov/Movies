package com.example.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.dto.ApiFactory
import com.example.data.dto.dtoModels.movieInfo.MovieInfoDto
import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review

class RepositoryMovieImpl() : RepositoryMovie {
    val apiServiceImpl = ApiFactory.apiService
    val mapper = MovieMapper()
    private val cachedData = mutableListOf<MovieInfoDto>()
    override suspend fun getMoviesList(): List<MovieInfo> {
        val topMovies = apiServiceImpl.getTopMovies()
        Log.d("MainActivity", "$topMovies")
        val ids = mapper.mapIdListToList(topMovies)
        for (id in ids) {
            val movieInfoDto = apiServiceImpl.getMoviesInfo(id)
            Log.d("MainActivity", "$movieInfoDto")
            cachedData.add(movieInfoDto)
        }
        return cachedData.map { mapper.mapDToToEntity(it) }
    }

    override fun getMovieInfo(id: Int): MovieInfo? {
        return cachedData.find { it.id == id }?.let { mapper.mapDToToEntity(it) }
    }

    override suspend fun getReviews(movieId: Int): List<Review> {
        val reviews = apiServiceImpl.getReviews(movieId=movieId).reviews.map {
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