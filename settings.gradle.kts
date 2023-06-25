pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    // TODO
    plugins {
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
