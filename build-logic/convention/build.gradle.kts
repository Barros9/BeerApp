import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.barros.beerapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("pluginAndroidApplication") {
            id = "beerapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("pluginAndroidLibrary") {
            id = "beerapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("pluginComposeApplication") {
            id = "beerapp.compose.application"
            implementationClass = "ComposeApplicationConventionPlugin"
        }
        register("pluginComposeLibrary") {
            id = "beerapp.compose.library"
            implementationClass = "ComposeLibraryConventionPlugin"
        }
        register("pluginDataStore") {
            id = "beerapp.datastore"
            implementationClass = "DataStoreConventionPlugin"
        }
        register("pluginHilt") {
            id = "beerapp.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("pluginNavigation") {
            id = "beerapp.navigation"
            implementationClass = "NavigationConventionPlugin"
        }
        register("pluginNetwork") {
            id = "beerapp.network"
            implementationClass = "NetworkConventionPlugin"
        }
        register("pluginRoom") {
            id = "beerapp.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("pluginTest") {
            id = "beerapp.test"
            implementationClass = "TestConventionPlugin"
        }
        register("pluginWork") {
            id = "beerapp.work"
            implementationClass = "WorkConventionPlugin"
        }
    }
}
