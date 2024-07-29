plugins {
    alias(libs.plugins.beerapp.android.library)
    alias(libs.plugins.beerapp.compose.library)
    alias(libs.plugins.beerapp.hilt)
    alias(libs.plugins.beerapp.test)
}

android {
    namespace = "com.barros.beerapp.features.home"
}

dependencies {
    // Libraries
    implementation(project(":libraries:beer"))
    implementation(project(":libraries:navigator"))
    implementation(project(":libraries:ui"))

    // Glide
    implementation(libs.landscapist.glide)

    // Temp
    implementation(libs.jetbrains.kotlin.serialization)
}
