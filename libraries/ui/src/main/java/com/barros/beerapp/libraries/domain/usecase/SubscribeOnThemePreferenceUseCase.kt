package com.barros.beerapp.libraries.domain.usecase

import com.barros.beerapp.libraries.domain.entity.Theme
import com.barros.beerapp.libraries.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeOnThemePreferenceUseCase @Inject constructor(
    private val repository: ThemePreferenceRepository
) {
    operator fun invoke(): Flow<Theme> = repository.subscribeOnThemePreference()
}
