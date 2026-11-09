package github.leavesczy.matisse.buildlogic.config

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.kotlin.dsl.getByType
import java.io.File

internal fun Project.configureAndroidApplication(applicationExtension: ApplicationExtension) {
    applicationExtension.apply {
        defaultConfig {
            applicationId = "github.leavesczy.matisse.samples"
            targetSdk {
                version = release(version = androidTargetSdkVersion())
            }
            versionCode = appVersionCode()
            versionName = appVersionName()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }
        val archiveTime = if (nonDebugPackageRequested()) {
            "_${providers.of(ApkBuildTimeValueSource::class.java) {}.get()}"
        } else {
            ""
        }
        project.extensions.getByType<BasePluginExtension>().archivesName.set(
            "matisse_v${appVersionName()}_${appVersionCode()}$archiveTime"
        )
        signingConfigs {
            create("release") {
                storeFile = File(rootDir, "key.jks")
                keyAlias = "leavesczy"
                keyPassword = "123456"
                storePassword = "123456"
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }
        buildTypes {
            debug {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = false
                isShrinkResources = false
                isDebuggable = true
                proguardFiles(
                    getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            release {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = true
                isShrinkResources = true
                isDebuggable = false
                proguardFiles(
                    getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                ndk {
                    abiFilters.apply {
                        clear()
                        add(element = "arm64-v8a")
                    }
                }
            }
        }
        packaging {
            jniLibs {
                excludes += setOf("META-INF/{AL2.0,LGPL2.1}")
            }
            resources {
                excludes += setOf(
                    "**/*.md",
                    "**/*.version",
                    "**/*.properties",
                    "**/*.kotlin_module",
                    "**/CHANGES",
                    "**/LICENSE.txt",
                    "**/{AL2.0,LGPL2.1}",
                    "**/DebugProbesKt.bin",
                    "**/app-metadata.properties",
                    "**/kotlin-tooling-metadata.json",
                    "**/version-control-info.textproto",
                    "**/androidsupportmultidexversion.txt"
                )
            }
        }
    }
}
