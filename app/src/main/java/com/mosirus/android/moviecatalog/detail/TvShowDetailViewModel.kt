package com.mosirus.android.moviecatalog.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.TvShow
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase

class TvShowDetailViewModel @ViewModelInject constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModel() {

    private val tvShowId = MutableLiveData<Int>()

    fun setSelectedTvShow(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    var tvShow: LiveData<Resource<TvShow>> = Transformations.switchMap(tvShowId) {
        catalogUseCase.getTvShowDetail(it).asLiveData()
    }

    fun setFavoriteTvShow() {
        val tvShow = tvShow.value?.data
        if (tvShow != null) {
            val newState = !tvShow.favorited
            catalogUseCase.setFavoriteTvShow(tvShow, newState)
        }
    }
}