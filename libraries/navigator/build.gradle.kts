plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

dependencies {
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
