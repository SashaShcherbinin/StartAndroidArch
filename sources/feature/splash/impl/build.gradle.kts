plugins {
    id(libs.plugins.common.android.library.module)
}

android {
    namespace = "feature.splash"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.get()
    }
    resourcePrefix("splash")
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.coroutines.core)
    implementation(libs.koin.core)

    implementation(projects.base.domain.api)
    implementation(projects.base.compose.impl)
    implementation(projects.feature.dashboard.api)
    implementation(projects.feature.splash.api)
}
