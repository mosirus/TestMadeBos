package com.mosirus.android.moviecatalog.core.data

import com.mosirus.android.moviecatalog.core.data.source.local.LocalDataSource
import com.mosirus.android.moviecatalog.core.data.source.remote.RemoteDataSource
import com.mosirus.android.moviecatalog.core.data.source.remote.api.ApiResponse
import com.mosirus.android.moviecatalog.core.data.source.remote.response.MovieResponse
import com.mosirus.android.moviecatalog.core.data.source.remote.response.TvShowResponse
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.domain.repository.ICatalogRepository
import com.mosirus.android.moviecatalog.core.utils.AppExecutors
import com.mosirus.android.moviecatalog.core.utils.MovieDataMapper
import com.mosirus.android.moviecatalog.core.utils.TvShowDataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICatalogRepository {

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies()
                    .map { MovieDataMapper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getPopularMovies()

            public override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = MovieDataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getPopularTvShows(): Flow<Resource<List<TvShow>>> {
        return object :
            NetworkBoundResource<List<TvShow>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShows()
                    .map { TvShowDataMapper.mapTvShowEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getPopularTvShows()

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = TvShowDataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<Movie>> {
        return object :
            NetworkBoundResource<Movie, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieDetail(movieId)
                    .map { MovieDataMapper.mapMovieEntityToDomain(it) }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null || data.tagline.isNullOrBlank()

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: MovieResponse) {
                val movie = MovieDataMapper.mapMovieResponseToEntity(data)
                localDataSource.updateMovie(movie)
            }
        }.asFlow()
    }

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShow>> {
        return object :
            NetworkBoundResource<TvShow, TvShowResponse>() {
            public override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getTvShowDetail(tvShowId)
                    .map { TvShowDataMapper.mapTvShowEntityToDomain(it) }
            }

            override fun shouldFetch(data: TvShow?): Boolean =
                data == null || data.tagline.isNullOrBlank()

            override suspend fun createCall(): Flow<ApiResponse<TvShowResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvShowResponse) {
                val tvShow = TvShowDataMapper.mapTvShowResponseToEntity(data)
                localDataSource.updateTvShow(tvShow)
            }
        }.asFlow()
    }

    override fun getFavoritedMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoritedMovies()
            .map { MovieDataMapper.mapMovieEntitiesToDomain(it) }
    }

    override fun getFavoritedTvShows(): Flow<List<TvShow>> {
        return localDataSource.getFavoritedTvShows()
            .map { TvShowDataMapper.mapTvShowEntitiesToDomain(it) }
    }


    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = MovieDataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute {
            CoroutineScope(Dispatchers.IO).launch {
                localDataSource.setMovieFavorite(movieEntity, state)
            }
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = TvShowDataMapper.mapTvShowDomainToEntity(tvShow)
        appExecutors.diskIO().execute {
            CoroutineScope(Dispatchers.IO).launch {
                localDataSource.setTvShowFavorite(tvShowEntity, state)
            }
        }
    }
}