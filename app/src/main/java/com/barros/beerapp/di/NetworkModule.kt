package com.barros.beerapp.di

import com.barros.beerapp.api.BeerApi
import com.barros.beerapp.api.BeerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideBeerApi(): BeerApi {
        return BeerService.create()
    }
}
