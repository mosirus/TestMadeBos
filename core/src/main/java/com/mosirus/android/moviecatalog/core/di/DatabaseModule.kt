package com.mosirus.android.moviecatalog.core.di

import android.content.Context
import androidx.room.Room
import com.mosirus.android.moviecatalog.core.data.source.local.room.CatalogDao
import com.mosirus.android.moviecatalog.core.data.source.local.room.CatalogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CatalogDatabase =
        Room.databaseBuilder(
            context,
            CatalogDatabase::class.java, "Catalog.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCatalogDao(database: CatalogDatabase): CatalogDao = database.catalogDao()
}