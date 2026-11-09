package github.leavesczy.matisse.buildlogic.config

import org.gradle.api.Project

private enum class AppBuildType(val typeName: String) {
    Debug(typeName = "debug"),
    Release(typeName = "release");
}

internal fun Project.nonDebugPackageRequested(): Boolean {
    return gradle.startParameter.taskNames.any { taskName ->
        val name = taskName.substringAfterLast(delimiter = ':')
        AppBuildType.entries.any { buildType ->
            buildType != AppBuildType.Debug &&
                    name.contains(other = buildType.typeName, ignoreCase = true)
        }
    }
}
