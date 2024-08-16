plugins {
    alias(libs.plugins.beerapp.android.library)
    alias(libs.plugins.beerapp.hilt)
    alias(libs.plugins.beerapp.navigation)
    alias(libs.plugins.beerapp.test)
}

android {
    namespace = "com.barros.beerapp.libraries.navigator"
}
