package com.mosirus.android.moviecatalog.favorite.di

import android.content.Context
import com.mosirus.android.moviecatalog.di.FavoriteModuleDependencies
import com.mosirus.android.moviecatalog.favorite.FavoriteActivity
import com.mosirus.android.moviecatalog.favorite.movie.FavMovieFragment
import com.mosirus.android.moviecatalog.favorite.tvshow.FavTvShowFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

    fun inject(activity: FavoriteActivity)
    fun inject(fragment: FavMovieFragment)
    fun inject(fragment: FavTvShowFragment)
}