package com.example.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.dto.dtoModels.movieInfo.PosterDto
import com.example.data.dto.dtoModels.movieInfo.RatingDto
import com.example.data.dto.dtoModels.movieInfo.TrailersListDto

@Entity("favourite_movies")
data class MovieInfoModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val year: Int?,
    val description: String?,
    @Embedded val rating: RatingDto,
    @Embedded val poster: PosterDto,
    @Embedded val trailersList: TrailersListDto?,
)