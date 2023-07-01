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
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("pluginAndroidApplication") {
            id = "beerapp.plugin.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("pluginAndroidLibrary") {
            id = "beerapp.plugin.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("pluginComposeApplication") {
            id = "beerapp.plugin.compose.application"
            implementationClass = "ComposeApplicationConventionPlugin"
        }
        register("pluginComposeLibrary") {
            id = "beerapp.plugin.compose.library"
            implementationClass = "ComposeLibraryConventionPlugin"
        }
        register("pluginHilt") {
            id = "beerapp.plugin.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("pluginNavigation") {
            id = "beerapp.plugin.navigation"
            implementationClass = "NavigationConventionPlugin"
        }
        register("pluginNetwork") {
            id = "beerapp.plugin.network"
            implementationClass = "NetworkConventionPlugin"
        }
        register("pluginRoom") {
            id = "beerapp.plugin.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("pluginTest") {
            id = "beerapp.plugin.test"
            implementationClass = "TestConventionPlugin"
        }
        register("pluginWork") {
            id = "beerapp.plugin.work"
            implementationClass = "WorkConventionPlugin"
        }
    }
}
