plugins {
    id(libs.plugins.common.kotlin.library.module)
}

dependencies {
    implementation(libs.bundles.network)
    testImplementation(libs.bundles.test.common)
}
