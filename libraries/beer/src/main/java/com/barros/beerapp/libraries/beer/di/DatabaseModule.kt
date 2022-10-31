package com.barros.beerapp.libraries.beer.di

import android.content.Context
import androidx.room.Room
import com.barros.beerapp.libraries.beer.data.database.BeerAppDatabase
import com.barros.beerapp.libraries.beer.data.database.BeerDao
import com.barros.beerapp.libraries.beer.data.database.KeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDb(
        @ApplicationContext context: Context,
    ): BeerAppDatabase =
        Room.databaseBuilder(context, BeerAppDatabase::class.java, "BeerAppDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBeerDao(beerAppDatabase: BeerAppDatabase): BeerDao = beerAppDatabase.beerDao()

    @Provides
    @Singleton
    fun provideKeyDao(beerAppDatabase: BeerAppDatabase): KeyDao = beerAppDatabase.keyDao()
}
