package github.leavesczy.matisse.buildlogic.config

import com.android.build.api.dsl.LibraryExtension
import java.io.File

internal fun configureAndroidLibrary(libraryExtension: LibraryExtension) {
    libraryExtension.apply {
        defaultConfig {
            consumerProguardFiles.add(File("consumer-rules.pro"))
        }
    }
}
