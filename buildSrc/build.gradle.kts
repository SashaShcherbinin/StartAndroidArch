plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    alias(libs.plugins.kotlin.serialization)
}
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.xml.builder)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.network)
}
