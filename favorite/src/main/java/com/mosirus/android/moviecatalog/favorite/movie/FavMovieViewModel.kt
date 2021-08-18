package com.mosirus.android.moviecatalog.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase

class FavMovieViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {

    fun getFavoriteMovies(): LiveData<List<Movie>> =
        catalogUseCase.getFavoritedMovies().asLiveData()
}