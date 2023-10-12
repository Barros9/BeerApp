import com.android.build.gradle.LibraryExtension
import com.barros.beerapp.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jlleitschuh.gradle.ktlint")
                apply("org.jetbrains.kotlinx.kover")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                buildFeatures {
                    buildConfig = true
                }

                with(defaultConfig) {
                    val version = providers.gradleProperty("android.versionName").get()
                    targetSdk = providers.gradleProperty("android.targetSdk").get().toInt()
                    versionCode = providers.gradleProperty("android.versionCode").get().toInt()
                    versionName = version
                    buildConfigField("String", "VERSION_NAME", "\"$version\"")
                }
            }

            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
            }
        }
    }
}