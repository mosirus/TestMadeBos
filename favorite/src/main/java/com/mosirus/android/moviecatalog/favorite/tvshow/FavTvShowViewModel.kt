package com.mosirus.android.moviecatalog.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase

class FavTvShowViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {

    fun getFavoriteTvShows(): LiveData<List<TvShow>> =
        catalogUseCase.getFavoritedTvShows().asLiveData()
}