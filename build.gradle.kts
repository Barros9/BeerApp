plugins {
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.kotlin.android").apply(false)
    id("com.google.dagger.hilt.android").apply(false)
    id("org.jlleitschuh.gradle.ktlint").apply(false)
    id("org.jetbrains.kotlinx.kover").apply(false)
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "kover")

    afterEvaluate {
        if (hasProperty("android")) {
            configure<com.android.build.gradle.BaseExtension> {
                compileSdkVersion(extra["android.compileSdkVersion"].toString().toInt())

                defaultConfig {
                    minSdk = extra["android.minSdk"].toString().toInt()
                    targetSdk = extra["android.targetSdk"].toString().toInt()
                    versionCode = extra["android.versionCode"].toString().toInt()
                    versionName = extra["android.versionName"].toString()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                packagingOptions {
                    resources.excludes.add("META-INF/*")
                }

                buildFeatures.compose = true
                composeOptions.kotlinCompilerExtensionVersion =
                    extra["settings.composeCompiler"].toString()
            }

            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_11.toString()
                }
            }
        }
    }

    plugins.withType(kotlinx.kover.KoverPlugin::class) {
        extensions.configure(kotlinx.kover.api.KoverMergedConfig::class) {
            enable()
            filters {
                classes {
                    excludes += listOf(
                        // Hilt
                        "*.di.*",
                        "dagger.hilt.**",
                        "hilt_aggregated_deps.*",
                        "*.*_Factory",
                        "*.*_MembersInjector",
                        "*.*_HiltModules*",
                        "*.Hilt_*",
                        "*.*_NavigatorModule",
                        "*_Factory\$*",

                        // Room
                        "*.*_Impl*",

                        // Compose
                        "*ComposableSingletons\$*",
                        "*ScreenKt",
                        "*ScreenKt\$*",

                        // App
                        "com.barros.beerapp.BeerAppApplication",
                        "com.barros.beerapp.MainActivity",
                        "com.barros.beerapp.MainActivity\$*",

                        // Libraries
                        "com.barros.beerapp.libraries.navigator.navigation.NavigatorModule",
                        "com.barros.beerapp.libraries.navigator.navigation.NavigationDestination",
                        "com.barros.beerapp.libraries.navigator.navigation.NavigationDestination\$*",
                        "com.barros.beerapp.libraries.navigator.navigation.Navigator\$*",
                        "com.barros.beerapp.libraries.ui.*",

                        // BuildConfig
                        "*.BuildConfig",
                    )
                }
            }
        }
    }
}
