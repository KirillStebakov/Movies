package com.example.data.repository

import com.example.data.db.MovieInfoDb
import com.example.data.dto.dtoModels.movieInfo.MovieInfoDto
import com.example.data.dto.dtoModels.movieInfo.PosterDto
import com.example.data.dto.dtoModels.movieInfo.RatingDto
import com.example.data.dto.dtoModels.movieInfo.TrailerDto
import com.example.data.dto.dtoModels.moviesList.MoviesIdListDto
import com.example.data.dto.dtoModels.reviews.ReviewDto
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.domain.entity.movieInfo.Poster
import com.example.domain.entity.movieInfo.Rating
import com.example.domain.entity.movieInfo.Trailer
import com.example.domain.entity.reviews.Review

class MovieMapper {
    fun mapIdListToList(idListDto: MoviesIdListDto): List<Int> = idListDto.movieId.map { it.id }

    fun mapDToToEntity(dto: MovieInfoDto): MovieInfo = MovieInfo(
        id = dto.id,
        name = dto.name,
        alternativeName = dto.alternativeName,
        year = dto.year,
        description = dto.description,
        rating = dto.rating?.toEntity(),
        poster = dto.poster?.toEntity(),
        trailersList = dto.trailersList?.trailers?.toEntity()
    )

    fun mapReviewDtoToEntity(reviewDto: ReviewDto): Review = Review(
        id = reviewDto.id,
        movieId = reviewDto.movieId,
        type = reviewDto.type,
        review = reviewDto.review,
        date = reviewDto.date,
        author = reviewDto.author
    )

    fun mapEntityToDbModel(movie: MovieInfo?): MovieInfoDb = MovieInfoDb(
        id = movie?.id,
        name = movie?.name,
        alternativeName = movie?.alternativeName,
        year = movie?.year,
        description = movie?.description,
        rating = movie?.rating,
        poster = movie?.poster,
    )

    fun mapDbModelToEntity(dbModel: MovieInfoDb?): MovieInfo? = MovieInfo(
        id = dbModel?.id,
        name = dbModel?.name,
        alternativeName = dbModel?.alternativeName,
        year = dbModel?.year,
        description = dbModel?.description,
        rating = dbModel?.rating?.toEntity(),
        poster = dbModel?.poster?.toEntity(),
    )

    fun mapListDbModelToListEntity(list: List<MovieInfoDb>): List<MovieInfo?> =
        list.map { mapDbModelToEntity(it) }


    fun List<TrailerDto>.toEntity(): List<Trailer> {
        return this.map {
            it.toEntity() as Trailer
        }
    }

    fun TrailerDto?.toEntity(): Trailer? = Trailer(
        url = this?.url ?: "",
        name = this?.name ?: "",
        site = this?.site ?: "",
        size = this?.size ?: 0,
        type = this?.type ?: ""
    )

    fun RatingDto?.toEntity(): Rating? = Rating(
        kp = this?.kp ?: 0.0
    )

    fun PosterDto?.toEntity(): Poster? = Poster(
        url = this?.url ?: "",
        previewUrl = this?.previewUrl ?: ""
    )

    fun Rating?.toEntity(): Rating? = Rating(
        kp = this?.kp ?: 0.0
    )

    fun Poster?.toEntity(): Poster? = Poster(
        url = this?.url ?: "",
        previewUrl = this?.previewUrl ?: ""
    )

}