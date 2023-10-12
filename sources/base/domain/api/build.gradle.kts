plugins {
    id(libs.plugins.common.kotlin.library.module)
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.coroutines.core)
}
