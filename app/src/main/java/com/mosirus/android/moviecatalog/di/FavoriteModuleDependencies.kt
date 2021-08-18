package com.mosirus.android.moviecatalog.di

import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FavoriteModuleDependencies {

    fun catalogUseCase(): CatalogUseCase
}