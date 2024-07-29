plugins {
    alias(libs.plugins.beerapp.android.library)
    alias(libs.plugins.beerapp.compose.library)
    alias(libs.plugins.beerapp.datastore)
    alias(libs.plugins.beerapp.hilt)
}

android {
    namespace = "com.barros.beerapp.libraries.ui"
}
