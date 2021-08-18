package com.mosirus.android.moviecatalog.core.di

import com.mosirus.android.moviecatalog.core.data.CatalogRepository
import com.mosirus.android.moviecatalog.core.domain.repository.ICatalogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(catalogRepository: CatalogRepository): ICatalogRepository
}