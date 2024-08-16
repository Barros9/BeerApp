package com.barros.beerapp.libraries.beer.di

import com.barros.beerapp.libraries.beer.data.repository.BeerRepositoryImpl
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface RepositoryModuleBindings {

    @Binds
    @Singleton
    fun bindBeerRepository(impl: BeerRepositoryImpl): BeerRepository
}
