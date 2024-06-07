plugins {
    alias(libs.plugins.qryptium.android.library)
    alias(libs.plugins.qryptium.jvm.ktor)
}

android {
    namespace = "com.fps.core.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.timber)

    implementation(projects.core.domain)
}