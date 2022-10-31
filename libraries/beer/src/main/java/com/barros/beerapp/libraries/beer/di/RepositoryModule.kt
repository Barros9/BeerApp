package com.barros.beerapp.libraries.beer.di

import com.barros.beerapp.libraries.beer.data.repository.BeerRepositoryImpl
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class RepositoryModule {

    @Singleton
    @Provides
    fun provideBeerRepository(beerRepository: BeerRepositoryImpl): BeerRepository = beerRepository
}
