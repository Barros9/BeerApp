package com.barros.beerapp.libraries.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.barros.beerapp.libraries.domain.entity.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ThemeDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ThemeDataSource {

    companion object {
        private const val THEME_PREFERENCE_KEY = "theme_preference_key"
    }

    override fun subscribeOnThemePreference(): Flow<Theme> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(THEME_PREFERENCE_KEY)]
                ?.let { Theme.valueOf(it) } ?: Theme.Light
        }

    override suspend fun saveThemePreferences(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(THEME_PREFERENCE_KEY)] = theme.name
        }
    }
}
