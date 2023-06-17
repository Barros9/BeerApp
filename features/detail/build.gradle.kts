plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

dependencies {
    // Libraries
    implementation(project(":libraries:beer"))
    implementation(project(":libraries:navigator"))
    implementation(project(":libraries:ui"))

    // Compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    // Glide
    implementation(libs.landscapist.glide)

    // Hilt
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation)

    // Test
    testImplementation(libs.bundles.test)

    // Android
    androidTestImplementation(libs.bundles.android.test)
}
