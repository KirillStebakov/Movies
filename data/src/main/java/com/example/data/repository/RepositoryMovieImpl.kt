package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.domain.RepositoryMovie
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.reviews.Review

class RepositoryMovieImpl(): RepositoryMovie {
    override fun getMoviesList(): LiveData<List<MovieInfo>> {
        TODO("Not yet implemented")
    }

    override fun getMovieInfo(): LiveData<MovieInfo> {
        TODO("Not yet implemented")
    }

    override fun getReviews(): LiveData<List<Review>> {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }
}