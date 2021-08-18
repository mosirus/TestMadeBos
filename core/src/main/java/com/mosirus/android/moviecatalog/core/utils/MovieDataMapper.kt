package com.mosirus.android.moviecatalog.core.utils

import com.mosirus.android.moviecatalog.core.data.source.local.entity.MovieEntity
import com.mosirus.android.moviecatalog.core.data.source.remote.response.MovieResponse
import com.mosirus.android.moviecatalog.core.domain.model.Movie

object MovieDataMapper {

    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.movieId,
                title = it.title,
                description = it.description,
                tagline = it.tagline,
                releaseDate = it.releaseDate,
                rating = it.rating,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                posterBgPath = it.posterBgPath,
                favorited = it.favorited
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapMovieResponseToEntity(input: MovieResponse) = MovieEntity(
        movieId = input.movieId,
        title = input.title,
        description = input.description,
        tagline = input.tagline,
        releaseDate = input.releaseDate,
        rating = input.rating,
        voteCount = input.voteCount,
        posterPath = input.posterPath,
        posterBgPath = input.posterBgPath,
        favorited = input.favorited
    )


    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                title = it.title,
                description = it.description,
                tagline = it.tagline,
                releaseDate = it.releaseDate,
                rating = it.rating,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                posterBgPath = it.posterBgPath,
                favorited = it.favorited
            )
        }

    fun mapMovieEntityToDomain(input: MovieEntity) = Movie(
        movieId = input.movieId,
        title = input.title,
        description = input.description,
        tagline = input.tagline,
        releaseDate = input.releaseDate,
        rating = input.rating,
        voteCount = input.voteCount,
        posterPath = input.posterPath,
        posterBgPath = input.posterBgPath,
        favorited = input.favorited
    )

    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        movieId = input.movieId,
        title = input.title,
        description = input.description,
        tagline = input.tagline,
        releaseDate = input.releaseDate,
        rating = input.rating,
        voteCount = input.voteCount,
        posterPath = input.posterPath,
        posterBgPath = input.posterBgPath,
        favorited = input.favorited
    )
}