import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.projectLibs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val VersionCatalog.javaVersion
    get() = JavaVersion.valueOf(
        findVersion("java").get().requiredVersion
    )

fun VersionCatalog.versionInt(name: String) = findVersion(name).get().requiredVersion.toInt()
