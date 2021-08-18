package com.mosirus.android.moviecatalog.di

import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogInteractor
import com.mosirus.android.moviecatalog.core.domain.usecase.CatalogUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideCatalogUseCase(catalogInteractor: CatalogInteractor): CatalogUseCase
}