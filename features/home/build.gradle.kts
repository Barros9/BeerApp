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
    // TODO solve this
//    implementation(platform(androidLibs.compose.bom))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation(androidLibs.bundles.compose)
    debugImplementation(androidLibs.bundles.compose.debug)

    // Glide
    implementation(androidLibs.glide.compose)

    // Hilt
    kapt(androidLibs.hilt.dagger.compiler)
    kapt(androidLibs.hilt.compiler)
    implementation(androidLibs.hilt.dagger.android)
    implementation(androidLibs.hilt.navigation.compose)

    // Paging
    implementation(androidLibs.paging.compose)

    // Test
    testImplementation(androidLibs.bundles.test)

    // Android
    androidTestImplementation(androidLibs.bundles.android.test)
}
