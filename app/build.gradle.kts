plugins {
    id("beerapp.plugin.android.application")
    id("beerapp.plugin.compose.application")
    id("beerapp.plugin.hilt")
    id("beerapp.plugin.navigation")
    id("beerapp.plugin.test")
    id("beerapp.plugin.work")
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

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        )
    }
}

dependencies {
    // Libraries
    implementation(project(":features:detail"))
    implementation(project(":features:home"))
    implementation(project(":libraries:beer"))
    implementation(project(":libraries:navigator"))
    implementation(project(":libraries:ui"))

    // Kover
    kover(project(":features:detail"))
    kover(project(":features:home"))
    kover(project(":libraries:beer"))
    kover(project(":libraries:navigator"))
    kover(project(":libraries:ui"))

    // Android
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
}

extensions.configure<kotlinx.kover.gradle.plugin.dsl.KoverReportExtension> {
    koverReport {
        filters {
            excludes {
                // Hilt
                classes("*.di.*")
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
