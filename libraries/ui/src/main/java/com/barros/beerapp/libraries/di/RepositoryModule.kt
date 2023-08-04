package com.barros.beerapp.libraries.di

import com.barros.beerapp.libraries.data.repository.ThemePreferenceRepositoryImpl
import com.barros.beerapp.libraries.domain.repository.ThemePreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class RepositoryModule {

    @Provides
    @Singleton
    fun provideThemePreferenceRepository(repository: ThemePreferenceRepositoryImpl): ThemePreferenceRepository =
        repository
}
