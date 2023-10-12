plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        targetSdk = projectLibs.versionInt("android.targetSdk")
        compileSdk = project.projectLibs.versionInt("android.compileSdk")
        minSdk = project.projectLibs.versionInt("android.minSdk")
    }
    compileOptions {
        sourceCompatibility = projectLibs.javaVersion
        targetCompatibility = projectLibs.javaVersion
    }
    kotlinOptions {
        jvmTarget = projectLibs.javaVersion.toString()
    }
}
