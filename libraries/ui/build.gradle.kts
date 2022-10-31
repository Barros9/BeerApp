plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    // Compose
    // TODO solve this
//    implementation(platform(androidLibs.compose.bom))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation(androidLibs.bundles.compose)
    debugImplementation(androidLibs.bundles.compose.debug)
}
