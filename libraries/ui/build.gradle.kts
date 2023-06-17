plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    // Compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)
}
