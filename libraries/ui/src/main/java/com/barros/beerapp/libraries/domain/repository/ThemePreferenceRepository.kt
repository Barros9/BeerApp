package com.barros.beerapp.libraries.domain.repository

import com.barros.beerapp.libraries.domain.entity.Theme
import kotlinx.coroutines.flow.Flow

interface ThemePreferenceRepository {
    fun subscribeOnThemePreference(): Flow<Theme>
    suspend fun saveThemePreferences(theme: Theme)
}
