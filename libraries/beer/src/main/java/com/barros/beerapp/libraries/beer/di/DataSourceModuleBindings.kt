package com.barros.beerapp.libraries.beer.di

import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSource
import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSourceImpl
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSource
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface DataSourceModuleBindings {

    @Binds
    @Singleton
    fun bindBeerLocalDataSource(impl: BeerLocalDataSourceImpl): BeerLocalDataSource

    @Binds
    @Singleton
    fun bindBeerRemoteDataSource(impl: BeerRemoteDataSourceImpl): BeerRemoteDataSource
}
