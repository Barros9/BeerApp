package com.barros.beerapp.di

import com.barros.beerapp.api.BeerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideBeerHttpClient(): HttpClient {
        return BeerService.createHttpClient()
    }
}
