package com.barros.beerapp.libraries.di

import com.barros.beerapp.libraries.data.datasource.ThemeDataSource
import com.barros.beerapp.libraries.data.datasource.ThemeDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DataSourceModule {

    @Provides
    @Singleton
    fun provideThemeDataSource(themeDataSource: ThemeDataSourceImpl): ThemeDataSource =
        themeDataSource
}
