import com.barros.beerapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "implementation"(libs.findLibrary("square-okhttp3").get())
                "implementation"(libs.findLibrary("square-okhttp3-logging").get())
                "implementation"(libs.findLibrary("square-retrofit2").get())
                "implementation"(libs.findLibrary("square-retrofit2-converter").get())
                "testImplementation"(libs.findLibrary("square-okhttp3-mockwebserver").get())
            }
        }
    }
}