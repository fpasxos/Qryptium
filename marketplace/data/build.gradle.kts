plugins {
    alias(libs.plugins.qryptium.android.library)
    alias(libs.plugins.qryptium.jvm.ktor)
}

android {
    namespace = "com.fps.marketplace.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.marketplace.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}