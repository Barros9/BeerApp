import com.android.build.gradle.api.AndroidBasePlugin
import com.barros.beerapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                add("ksp", libs.findLibrary("dagger.hilt.compiler").get())
            }

            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("dagger.hilt.android.plugin")
                dependencies {
                    add("ksp", libs.findLibrary("androidx.hilt.compiler").get())
                    add("implementation", libs.findLibrary("dagger.hilt.android").get())
                    add("implementation", libs.findLibrary("androidx.hilt.navigation").get())
                    add("implementation", libs.findLibrary("androidx.hilt.work").get())
                }
            }
        }
    }
}