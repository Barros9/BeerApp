package com.barros.beerapp.libraries.data.datasource

import com.barros.beerapp.libraries.domain.entity.Theme
import kotlinx.coroutines.flow.Flow

internal interface ThemeDataSource {
    fun subscribeOnThemePreference(): Flow<Theme>
    suspend fun saveThemePreferences(theme: Theme)
}
