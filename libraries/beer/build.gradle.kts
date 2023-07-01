plugins {
    id("beerapp.plugin.android.library")
    id("beerapp.plugin.hilt")
    id("beerapp.plugin.network")
    id("beerapp.plugin.room")
    id("beerapp.plugin.test")
    id("beerapp.plugin.work")
}

android {
    namespace = "com.barros.beerapp.libraries.beer"
}
