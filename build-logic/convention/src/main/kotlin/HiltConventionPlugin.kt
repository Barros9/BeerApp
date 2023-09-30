import com.barros.beerapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                // TODO - KAPT must go last to avoid build warnings.
                // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                "kapt"(libs.findLibrary("dagger.hilt.compiler").get())
                "kapt"(libs.findLibrary("androidx.hilt.compiler").get())
                "implementation"(libs.findLibrary("dagger.hilt.android").get())
                "implementation"(libs.findLibrary("androidx.hilt.navigation").get())
                "implementation"(libs.findLibrary("androidx.hilt.work").get())
            }
        }
    }
}