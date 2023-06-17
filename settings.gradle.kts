pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
}

rootProject.name = extra["settings.projectName"].toString()
include(":app")
include(":features:detail")
include(":features:home")
include(":libraries:beer")
include(":libraries:navigator")
include(":libraries:ui")
