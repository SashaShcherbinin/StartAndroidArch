plugins {
    id(libs.plugins.common.android.library.module)
}

android {
    namespace = "feature.dashboard"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.get()
    }
    resourcePrefix("dashboard")
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.coroutines.core)
    implementation(libs.image.coil)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    debugImplementation(libs.compose.tooling)

    implementation(projects.base.compose.impl)
    implementation(projects.base.logger.api)
    implementation(projects.feature.dashboard.api)
    implementation(projects.feature.user.api)
}
