package com.barros.beerapp.di

import android.content.Context
import com.barros.beerapp.database.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideBeerDatabase(@ApplicationContext context: Context) = BeerDatabase.getInstance(context)
}
