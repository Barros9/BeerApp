import com.barros.beerapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
            dependencies {
                "implementation"(libs.findLibrary("androidx.navigation").get())
                "implementation"(libs.findLibrary("jetbrains.kotlin.serialization").get())
            }
        }
    }
}