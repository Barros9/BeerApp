package com.barros.beerapp.libraries.beer.di

import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSource
import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSourceImpl
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSource
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DataSourceModule {

    @Singleton
    @Provides
    fun provideBeerLocalDataSource(beerLocalDataSource: BeerLocalDataSourceImpl): BeerLocalDataSource =
        beerLocalDataSource

    @Singleton
    @Provides
    fun provideBeerRemoteDataSource(beerRemoteDataSource: BeerRemoteDataSourceImpl): BeerRemoteDataSource =
        beerRemoteDataSource
}
