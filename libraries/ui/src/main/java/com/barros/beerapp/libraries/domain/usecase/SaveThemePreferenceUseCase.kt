package com.barros.beerapp.libraries.domain.usecase

import com.barros.beerapp.libraries.domain.entity.Theme
import com.barros.beerapp.libraries.domain.repository.ThemePreferenceRepository
import javax.inject.Inject

class SaveThemePreferenceUseCase @Inject constructor(
    private val repository: ThemePreferenceRepository
) {
    suspend operator fun invoke(theme: Theme) = repository.saveThemePreferences(theme)
}
