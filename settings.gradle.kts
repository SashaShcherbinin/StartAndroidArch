enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")

// include submodules automatically
includeRecursive(dir = file("sources"), dirModuleName = "")

fun includeRecursive(dir: File, dirModuleName: String) {
    if (dir.isModule()) {
        include(dirModuleName)
        project(dirModuleName).projectDir = dir
    } else {
        dir.listFiles()
            ?.filter { it.isDirectory }
            ?.forEach { subDir ->
                includeRecursive(
                    dir = subDir,
                    dirModuleName = "$dirModuleName:${subDir.name}",
                )
            }
    }
}

fun File.isModule(): Boolean {
    return fileExists("build.gradle") || fileExists("build.gradle.kts")
}

fun File.fileExists(path: String): Boolean {
    return File(this, path).isFile
}