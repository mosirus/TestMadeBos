package com.mosirus.android.moviecatalog.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mosirus.android.moviecatalog.core.data.Resource
import com.mosirus.android.moviecatalog.core.domain.model.Movie
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase

class MovieDetailViewModel @ViewModelInject constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModel() {

    private val movieId = MutableLiveData<Int>()

    fun setSelectedMovie(movieId: Int) {
        this.movieId.value = movieId
    }

    var movie: LiveData<Resource<Movie>> = Transformations.switchMap(movieId) {
        catalogUseCase.getMovieDetail(it).asLiveData()
    }

    fun setFavoriteMovie() {
        val movie = movie.value?.data
        if (movie != null) {
            val newState = !movie.favorited
            catalogUseCase.setFavoriteMovie(movie, newState)
        }
    }
}