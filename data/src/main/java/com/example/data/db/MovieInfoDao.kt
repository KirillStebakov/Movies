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
    fun getFavoriteMovie(id: Int): LiveData<MovieInfoDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieToFavorite()

    @Query("DELETE FROM favourite_movies WHERE id=:id")
    fun deleteMovieFromFavorite(id: Int)
}