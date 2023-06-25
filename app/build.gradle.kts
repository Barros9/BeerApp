plugins {
    id("beerapp.plugin.android.application")
    id("beerapp.plugin.compose.application")
    id("beerapp.plugin.hilt")
    id("beerapp.plugin.navigation")
    id("beerapp.plugin.test")
}

android {
    namespace = "com.barros.beerapp"

    defaultConfig {
        applicationId = extra["android.applicationId"].toString()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // Libraries
    implementation(project(":features:detail"))
    implementation(project(":features:home"))
    implementation(project(":libraries:beer"))
    implementation(project(":libraries:navigator"))
    implementation(project(":libraries:ui"))

    // Android
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
}
