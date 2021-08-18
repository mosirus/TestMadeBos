package com.mosirus.android.moviecatalog.core.data.source.remote

import android.util.Log
import com.mosirus.android.moviecatalog.core.data.source.remote.api.ApiInterface
import com.mosirus.android.moviecatalog.core.data.source.remote.api.ApiResponse
import com.mosirus.android.moviecatalog.core.data.source.remote.response.MovieResponse
import com.mosirus.android.moviecatalog.core.data.source.remote.response.TvShowResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getPopularMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiInterface.getPopularMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPopularTvShows(): Flow<ApiResponse<List<TvShowResponse>>> {
        return flow {
            try {
                val response = apiInterface.getPopularTvShows()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieResponse>> {
        return flow {
            try {
                val dataResponse = apiInterface.getMovieDetail(movieId)
                emit(ApiResponse.Success(dataResponse))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvShowResponse>> {
        return flow {
            try {
                val dataResponse = apiInterface.getTvShowDetail(tvShowId)
                emit(ApiResponse.Success(dataResponse))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}