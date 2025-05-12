package com.example.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieInfoDao {

    @Query("SELECT * FROM favourite_movies")
    fun getFavoriteMovieList(): LiveData<List<MovieInfoDb>>

    @Query("SELECT * FROM favourite_movies WHERE id == :id LIMIT 1")
    suspend fun getFavoriteMovie(id: Int): MovieInfoDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToFavorite(movieInfoDb: MovieInfoDb)

    @Query("DELETE FROM favourite_movies WHERE id=:id")
    suspend fun deleteMovieFromFavorite(id: Int?)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_movies WHERE id = :id)")
    fun isFavorite(id: Int): LiveData<Boolean>
}