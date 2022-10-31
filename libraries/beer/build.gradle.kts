plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

dependencies {
    // Retrofit
    implementation(androidLibs.bundles.okhttp3)
    implementation(androidLibs.bundles.retrofit)

    // Serialization
    implementation(androidLibs.kotlinx.serialization)

    // Room
    implementation(androidLibs.bundles.room)
    kapt(androidLibs.room.compiler)

    // Hilt
    kapt(androidLibs.hilt.dagger.compiler)
    kapt(androidLibs.hilt.compiler)
    implementation(androidLibs.hilt.dagger.android)
    implementation(androidLibs.hilt.navigation.compose)

    // Paging
    implementation(androidLibs.paging.compose)

    // Test
    testImplementation(androidLibs.bundles.test)
}
