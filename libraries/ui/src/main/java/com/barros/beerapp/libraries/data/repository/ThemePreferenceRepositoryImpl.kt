package com.barros.beerapp.libraries.data.repository

import com.barros.beerapp.libraries.data.datasource.ThemeDataSource
import com.barros.beerapp.libraries.domain.entity.Theme
import com.barros.beerapp.libraries.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ThemePreferenceRepositoryImpl @Inject constructor(
    private val themeDataSource: ThemeDataSource
) : ThemePreferenceRepository {

    override fun subscribeOnThemePreference(): Flow<Theme> =
        themeDataSource.subscribeOnThemePreference()

    override suspend fun saveThemePreferences(theme: Theme) =
        themeDataSource.saveThemePreferences(theme)
}
