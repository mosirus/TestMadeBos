package com.mosirus.android.moviecatalog.core.domain.usecase

import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.domain.repository.ICatalogRepository
import javax.inject.Inject

class CatalogInteractor @Inject constructor(private val catalogRepository: ICatalogRepository) :
    CatalogUseCase {

    override fun getPopularMovies() = catalogRepository.getPopularMovies()

    override fun getPopularTvShows() = catalogRepository.getPopularTvShows()

    override fun getMovieDetail(movieId: Int) = catalogRepository.getMovieDetail(movieId)

    override fun getTvShowDetail(tvShowId: Int) = catalogRepository.getTvShowDetail(tvShowId)

    override fun getFavoritedMovies() = catalogRepository.getFavoritedMovies()

    override fun getFavoritedTvShows() = catalogRepository.getFavoritedTvShows()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        catalogRepository.setFavoriteMovie(movie, state)

    override fun setFavoriteTvShow(tvShow: TvShow, state: Boolean) =
        catalogRepository.setFavoriteTvShow(tvShow, state)
}