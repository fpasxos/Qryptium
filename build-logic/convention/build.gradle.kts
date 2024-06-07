plugins {
    `kotlin-dsl`
}

group = "com.fps.qryptium.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.ksp.gradlePlugin)
}

// we sort of 'register' all the convention plugins here
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "qryptium.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "qryptium.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "qryptium.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "qryptium.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "qryptium.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("jvmLibrary") {
            id = "qryptium.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor") {
            id = "qryptium.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}