package com.mosirus.android.moviecatalog.favorite.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase
import com.mosirus.android.moviecatalog.favorite.movie.FavMovieViewModel
import com.mosirus.android.moviecatalog.favorite.tvshow.FavTvShowViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavMovieViewModel::class.java) -> {
                FavMovieViewModel(catalogUseCase) as T
            }
            modelClass.isAssignableFrom(FavTvShowViewModel::class.java) -> {
                FavTvShowViewModel(catalogUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}