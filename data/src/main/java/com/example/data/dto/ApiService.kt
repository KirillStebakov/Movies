package com.example.data.dto

import com.example.data.BuildConfig
import com.example.data.dto.dtoModels.movieInfo.MovieInfoDto
import com.example.data.dto.dtoModels.moviesList.MoviesIdListDto
import com.example.data.dto.dtoModels.reviews.ReviewListDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers(API_KEY)
    @GET("v1.4/movie?field=rating.kp&search=0-10&sortField=votes.kp&sortType=1")
    suspend fun getTopMovies(
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_PAGE) page: Int,
    ): MoviesIdListDto

    @Headers(API_KEY)
    @GET("v1.4/movie/{id}")
    suspend fun getMoviesInfo(
        @Path(PATH_PARAM_ID) id: Int,
    ): MovieInfoDto

    @Headers(API_KEY)
    @GET("v1.4/reviews")
    suspend fun getReviews(
        @Query(QUERY_PARAM_LIMIT) limit: Int = 3,
        @Query(QUERY_PARAM_PAGE) page: Int = 1,
        @Query(QUERY_PARAM_MOVIE_ID) movieId: Int
    ): ReviewListDto

    companion object {
        private const val API_KEY = "X-API-KEY: ${BuildConfig.API_KEY}"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_PAGE = "page"
        private const val PATH_PARAM_ID = "id"
        private const val QUERY_PARAM_MOVIE_ID = "movieId"
    }
}