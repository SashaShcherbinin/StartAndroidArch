plugins {
    id(libs.plugins.common.android.application.module)
    alias(libs.plugins.google.firebase.crashlytics)
}

object Version {
    private const val MAJOR = 1
    private const val MINOR = 0
    private const val PATCH = 0
    const val CODE = MAJOR * 10000 + MINOR * 100 + PATCH
    const val NAME = "$MAJOR.$MINOR.$PATCH"
}

android {
    namespace = "com.start"
    defaultConfig {
        applicationId = "start.project"
        versionCode = Version.CODE
        versionName = Version.NAME
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.get()
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.feature.splash.api)
    implementation(projects.feature.splash.impl)
    implementation(projects.feature.dashboard.api)
    implementation(projects.feature.dashboard.impl)
    implementation(projects.feature.user.api)
    implementation(projects.feature.user.impl)
    implementation(projects.base.logger.api)
    implementation(projects.base.logger.impl)
    implementation(projects.base.network.impl)
    implementation(projects.base.storage.impl)
    implementation(projects.base.compose.impl)
    implementation(projects.base.domain.api)

    implementation(libs.google.material)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin.android)
}