package com.mosirus.android.moviecatalog.core.data.source.local

import com.mosirus.android.moviecatalog.core.data.source.local.entity.MovieEntity
import com.mosirus.android.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.mosirus.android.moviecatalog.core.data.source.local.room.CatalogDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mCatalogDao: CatalogDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = mCatalogDao.getMovies()

    fun getMovieDetail(movieId: Int): Flow<MovieEntity> = mCatalogDao.getMovieDetail(movieId)

    fun getFavoritedMovies(): Flow<List<MovieEntity>> =
        mCatalogDao.getFavoritedMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = mCatalogDao.insertMovies(movies)

    suspend fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mCatalogDao.updateMovie(movie)
    }

    suspend fun updateMovie(movie: MovieEntity) = mCatalogDao.updateMovie(movie)

    fun getAllTvShows(): Flow<List<TvShowEntity>> = mCatalogDao.getTvShows()

    fun getTvShowDetail(tvShowId: Int): Flow<TvShowEntity> =
        mCatalogDao.getTvShowDetail(tvShowId)

    fun getFavoritedTvShows(): Flow<List<TvShowEntity>> =
        mCatalogDao.getFavoritedTvShows()

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = mCatalogDao.insertTvShows(tvShows)

    suspend fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorited = newState
        mCatalogDao.updateTvShow(tvShow)
    }

    suspend fun updateTvShow(tvShow: TvShowEntity) = mCatalogDao.updateTvShow(tvShow)
}