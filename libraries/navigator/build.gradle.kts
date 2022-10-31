plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

dependencies {
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
