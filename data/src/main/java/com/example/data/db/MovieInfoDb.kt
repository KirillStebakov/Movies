package com.example.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.movieInfo.Poster
import com.example.domain.entity.movieInfo.Rating

@Entity(tableName = "favourite_movies")
data class MovieInfoDb(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val alternativeName: String?,
    val year: Int?,
    val description: String?,
    @Embedded val rating: Rating?,
    @Embedded val poster: Poster?,
)