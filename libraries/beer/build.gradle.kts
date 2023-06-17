plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

dependencies {
    // Retrofit
    implementation(libs.bundles.okhttp3)
    implementation(libs.bundles.retrofit)

    // Serialization
    implementation(libs.kotlinx.serialization)

    // Room
    kapt(libs.androidx.room.compiler)
    implementation(libs.bundles.room)

    // Hilt
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation)

    // Test
    testImplementation(libs.bundles.test)
}
