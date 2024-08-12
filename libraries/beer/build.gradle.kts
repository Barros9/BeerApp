plugins {
    alias(libs.plugins.beerapp.android.library)
    alias(libs.plugins.beerapp.hilt)
    alias(libs.plugins.beerapp.network)
    alias(libs.plugins.beerapp.room)
    alias(libs.plugins.beerapp.test)
    alias(libs.plugins.beerapp.work)
}

android {
    namespace = "com.barros.beerapp.libraries.beer"

    sourceSets {
        getByName("main").java.srcDir("src/shared")
        getByName("test").java.srcDir("src/shared")
    }
}
