pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.library") version extra["settings.androidGradle"].toString()
        id("com.android.application") version extra["settings.androidGradle"].toString()
        id("org.jetbrains.kotlin.android") version extra["settings.kotlin"].toString()
        id("com.google.dagger.hilt.android") version extra["settings.daggerHilt"].toString()
        id("org.jlleitschuh.gradle.ktlint") version extra["settings.ktlint"].toString()
        id("org.jetbrains.kotlinx.kover") version extra["settings.kover"].toString()
        kotlin("plugin.serialization") version extra["settings.kotlin"].toString()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("androidLibs") {
            version("core", "1.10.1")
            version("activity-compose", "1.7.2")
            version("navigation-compose", "2.5.3")
            version("glide-compose", "2.2.1")
            version("hilt-dagger", "2.46.1")
            version("hilt", "1.0.0")
            version("okhttp", "4.11.0")
            version("retrofit", "2.9.0")
            version("kotlinx-serialization", "1.5.1")
            version("kotlinx-serialization-retrofit-converter", "1.0.0")
            version("compose-bom", "2023.05.01")
            version("room", "2.5.1")
            version("junit", "1.1.5")
            version("mockk", "1.13.5")
            version("mock-web-server", "4.11.0")
            version("coroutines", "1.7.1")
            version("robolectric", "4.10.3")
            version("turbine", "0.13.0")

            library("core", "androidx.core", "core-ktx").versionRef("core")

            library(
                "activity-compose",
                "androidx.activity",
                "activity-compose"
            ).versionRef("activity-compose")

            library(
                "navigation-compose",
                "androidx.navigation",
                "navigation-compose"
            ).versionRef("navigation-compose")

            library(
                "hilt-dagger-compiler",
                "com.google.dagger",
                "hilt-compiler"
            ).versionRef("hilt-dagger")
            library(
                "hilt-dagger-android",
                "com.google.dagger",
                "hilt-android"
            ).versionRef("hilt-dagger")
            library("hilt-compiler", "androidx.hilt", "hilt-compiler").versionRef("hilt")
            library(
                "hilt-navigation-compose",
                "androidx.hilt",
                "hilt-navigation-compose"
            ).versionRef("hilt")

            library(
                "glide-compose",
                "com.github.skydoves",
                "landscapist-glide"
            ).versionRef("glide-compose")

            library("compose-bom", "androidx.compose", "compose-bom").version("compose-bom")

            library("compose-material3", "androidx.compose.material3", "material3").withoutVersion()
            library(
                "compose-ui-tooling-preview",
                "androidx.compose.ui",
                "ui-tooling-preview"
            ).withoutVersion()
            bundle("compose", listOf("compose-material3", "compose-ui-tooling-preview"))

            library("compose-ui-tooling", "androidx.compose.ui", "ui-tooling").withoutVersion()
            library(
                "compose-ui-test-manifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).withoutVersion()
            bundle("compose-debug", listOf("compose-ui-tooling", "compose-ui-test-manifest"))

            library("okhttp3", "com.squareup.okhttp3", "okhttp").versionRef("okhttp")
            library(
                "okhttp3-logging-interceptor",
                "com.squareup.okhttp3",
                "logging-interceptor"
            ).versionRef("okhttp")
            bundle("okhttp3", listOf("okhttp3", "okhttp3-logging-interceptor"))

            library("retrofit", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library(
                "retrofit-kotlinx-converter",
                "com.jakewharton.retrofit",
                "retrofit2-kotlinx-serialization-converter"
            ).versionRef("kotlinx-serialization-retrofit-converter")
            bundle("retrofit", listOf("retrofit", "retrofit-kotlinx-converter"))

            library(
                "kotlinx-serialization",
                "org.jetbrains.kotlinx",
                "kotlinx-serialization-json"
            ).versionRef("kotlinx-serialization")

            library("room-compiler", "androidx.room", "room-compiler").versionRef("room")
            library("room", "androidx.room", "room-runtime").versionRef("room")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("room")
            library("room-test", "androidx.room", "room-testing").versionRef("room")
            bundle("room", listOf("room", "room-ktx"))

            library(
                "mock-web-server",
                "com.squareup.okhttp3",
                "mockwebserver"
            ).versionRef("mock-web-server")
            library("robolectric", "org.robolectric", "robolectric").versionRef("robolectric")
            library("junit-ktx", "androidx.test.ext", "junit-ktx").versionRef("junit")
            library(
                "coroutines-test",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-test"
            ).versionRef("coroutines")
            library("mockk", "io.mockk", "mockk").versionRef("mockk")
            library("turbine", "app.cash.turbine", "turbine").versionRef("turbine")
            bundle(
                "test",
                listOf(
                    "coroutines-test",
                    "mockk",
                    "junit-ktx",
                    "room-test",
                    "robolectric",
                    "mock-web-server",
                    "turbine"
                )
            )

            library("mockk-android", "io.mockk", "mockk-android").versionRef("mockk")
            library("compose-android", "androidx.compose.ui", "ui-test-junit4").withoutVersion()
            bundle("android-test", listOf("mockk-android", "compose-android"))
        }
    }
}

rootProject.name = extra["settings.projectName"].toString()
include(":app")
include(":features:detail")
include(":features:home")
include(":libraries:beer")
include(":libraries:navigator")
include(":libraries:ui")
