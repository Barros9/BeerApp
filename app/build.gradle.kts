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
    implementation(androidLibs.core)
    implementation(androidLibs.activity.compose)

    // Compose
    // TODO solve this
//    implementation(platform(androidLibs.compose.bom))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation(androidLibs.bundles.compose)
    debugImplementation(androidLibs.bundles.compose.debug)

    // Hilt
    kapt(androidLibs.hilt.dagger.compiler)
    kapt(androidLibs.hilt.compiler)
    implementation(androidLibs.hilt.dagger.android)
    implementation(androidLibs.hilt.navigation.compose)

    // Navigation
    implementation(androidLibs.navigation.compose)

    // Test
    testImplementation(androidLibs.bundles.test)
}
