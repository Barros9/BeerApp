import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension

plugins {
    alias(libs.plugins.beerapp.android.application)
    alias(libs.plugins.beerapp.compose.application)
    alias(libs.plugins.beerapp.hilt)
    alias(libs.plugins.beerapp.navigation)
    alias(libs.plugins.beerapp.test)
    alias(libs.plugins.beerapp.work)
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
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    // Libraries
    implementation(project(":features:detail"))
    implementation(project(":features:home"))
    implementation(project(":libraries:beer"))
    implementation(project(":libraries:ui"))

    // Kover
    kover(project(":features:detail"))
    kover(project(":features:home"))
    kover(project(":libraries:beer"))
    kover(project(":libraries:ui"))

    // Android
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)
}

extensions.configure<KoverProjectExtension>("kover") {
    reports {
        filters {
            excludes {
                // Hilt
                classes("*.di.*")
                classes("dagger.hilt.**")
                classes("hilt_aggregated_deps.*")
                classes("*.*_Factory")
                classes("*.*_MembersInjector")
                classes("*.*_HiltModules*")
                classes("*.Hilt_*")
                classes("*.*_NavigatorModule")
                classes("*_Factory\$*")

                // Room
                classes("*.*_Impl*")

                // Compose
                classes("*ComposableSingletons\$*")
                classes("*ScreenKt")
                classes("*ScreenKt\$*")

                // App
                classes("com.barros.beerapp.BeerAppApplication")
                classes("com.barros.beerapp.MainActivity")
                classes("com.barros.beerapp.MainActivity\$*")

                // Libraries
                classes("com.barros.beerapp.libraries.navigator.navigation.NavigatorModule")
                classes("com.barros.beerapp.libraries.navigator.navigation.NavigationDestination")
                classes("com.barros.beerapp.libraries.navigator.navigation.NavigationDestination\$*")
                classes("com.barros.beerapp.libraries.navigator.navigation.Navigator\$*")
                packages("com.barros.beerapp.libraries.ui")

                // BuildConfig
                classes("*.BuildConfig")
            }
        }
    }
}
