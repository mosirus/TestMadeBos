package com.mosirus.android.moviecatalog.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class TvShowViewModel @ViewModelInject constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModel() {

    fun getAllTvShows(): LiveData<Resource<List<TvShow>>> =
        catalogUseCase.getPopularTvShows().asLiveData()
}