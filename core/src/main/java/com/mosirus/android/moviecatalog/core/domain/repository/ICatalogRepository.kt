package com.mosirus.android.moviecatalog.core.domain.repository

import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface ICatalogRepository {

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    fun getPopularTvShows(): Flow<Resource<List<TvShow>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<Movie>>

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShow>>

    fun getFavoritedMovies(): Flow<List<Movie>>

    fun getFavoritedTvShows(): Flow<List<TvShow>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun setFavoriteTvShow(tvShow: TvShow, state: Boolean)
}