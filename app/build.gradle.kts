plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
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

    // Compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    // Hilt
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation)

    // Navigation
    implementation(libs.androidx.navigation)

    // Test
    testImplementation(libs.bundles.test)
}
