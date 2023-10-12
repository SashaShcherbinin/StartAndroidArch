plugins {
    id(libs.plugins.common.android.library.module)
}

android {
    namespace = "base.compose"
    resourcePrefix = "base_compose"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.get()
    }
}

dependencies {
    implementation(libs.compose.material)
    implementation(libs.compose.preview)
    implementation(libs.compose.navigation)
    implementation(libs.compose.systemuicontroller)
}
