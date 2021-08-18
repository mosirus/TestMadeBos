package com.mosirus.android.moviecatalog.core.data.source.local.room

import androidx.room.*
import com.mosirus.android.moviecatalog.core.data.source.local.entity.MovieEntity
import com.mosirus.android.moviecatalog.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    @Query("SELECT * FROM movie_entities")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE favorited == 1")
    fun getFavoritedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE movieId = :movieId")
    fun getMovieDetail(movieId: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM tv_show_entities")
    fun getTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tv_show_entities WHERE favorited == 1")
    fun getFavoritedTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tv_show_entities WHERE tvShowId = :tvShowId")
    fun getTvShowDetail(tvShowId: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(movies: List<TvShowEntity>)

    @Update
    suspend fun updateTvShow(tvShow: TvShowEntity)
}