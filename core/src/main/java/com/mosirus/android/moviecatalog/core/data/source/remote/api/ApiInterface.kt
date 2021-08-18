package com.mosirus.android.moviecatalog.core.data.source.remote.api

import com.mosirus.android.moviecatalog.core.data.source.remote.response.MovieResponse
import com.mosirus.android.moviecatalog.core.data.source.remote.response.Response
import com.mosirus.android.moviecatalog.core.data.source.remote.response.TvShowResponse
import com.mosirus.android.moviecatalog.core.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MovieResponse>

    @GET("discover/tv")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<TvShowResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieResponse

    @GET("tv/{tvShowId}")
    suspend fun getTvShowDetail(
        @Path("tvShowId") tvShowId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): TvShowResponse
}