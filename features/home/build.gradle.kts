plugins {
    id("beerapp.plugin.android.library")
    id("beerapp.plugin.compose.library")
    id("beerapp.plugin.hilt")
    id("beerapp.plugin.test")
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
}
