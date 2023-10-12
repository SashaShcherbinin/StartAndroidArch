import task.localisation.LocalisationTask
import task.localisation.TASK_GROUP
import task.localisation.UpdateJsonTask

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
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

tasks.register<LocalisationTask>("translateString") {
    group = TASK_GROUP
    resourcePrefix = android.resourcePrefix ?: ""
}

tasks.register<UpdateJsonTask>("updateJson") {
    group = TASK_GROUP
    resourcePrefix = android.resourcePrefix ?: ""
}