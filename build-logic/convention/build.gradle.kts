import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "github.leavesczy.matisse.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.maven.publish.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.app.android.application.get().pluginId
            implementationClass = "github.leavesczy.matisse.buildlogic.ApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.app.android.library.get().pluginId
            implementationClass = "github.leavesczy.matisse.buildlogic.LibraryConventionPlugin"
        }
        register("androidxCompose") {
            id = libs.plugins.app.androidx.compose.get().pluginId
            implementationClass = "github.leavesczy.matisse.buildlogic.ComposeConventionPlugin"
        }
        register("kotlinParcelize") {
            id = libs.plugins.app.kotlin.parcelize.get().pluginId
            implementationClass = "github.leavesczy.matisse.buildlogic.ParcelizeConventionPlugin"
        }
        register("libraryPublish") {
            id = libs.plugins.app.library.publish.get().pluginId
            implementationClass =
                "github.leavesczy.matisse.buildlogic.AndroidLibraryPublishConventionPlugin"
        }
    }
}
