package com.mosirus.android.moviecatalog.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MovieViewModel @ViewModelInject constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModel() {

    fun getAllMovies(): LiveData<Resource<List<Movie>>> =
        catalogUseCase.getPopularMovies().asLiveData()
}