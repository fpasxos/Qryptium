plugins {
    alias(libs.plugins.qryptium.android.library.compose)
}

android {
    namespace = "com.fps.core.presentation.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.appcompat)
    api(libs.androidx.compose.material3)
    implementation(libs.material)
}