plugins {
    id(libs.plugins.common.android.library.module)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "base.network"
}

dependencies {
    implementation(projects.base.domain.api)
    implementation(projects.base.logger.api)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.network)
}
