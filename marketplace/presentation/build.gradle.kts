plugins {
    alias(libs.plugins.qryptium.android.feature.ui)
}

android {
    namespace = "com.fps.marketplace.presentation"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.compose.svg)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.marketplace.domain)
}