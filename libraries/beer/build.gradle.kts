plugins {
    id("beerapp.plugin.android.library")
    id("beerapp.plugin.hilt")
    id("beerapp.plugin.network")
    id("beerapp.plugin.room")
    id("beerapp.plugin.test")
    kotlin("plugin.serialization") //  TODO check this
}

android {
    namespace = "com.barros.beerapp.libraries.beer"
}

dependencies {
    implementation(libs.jetbrains.serialization)
}