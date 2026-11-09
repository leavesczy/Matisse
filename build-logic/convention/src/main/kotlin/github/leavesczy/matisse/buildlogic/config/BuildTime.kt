package github.leavesczy.matisse.buildlogic.config

import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal abstract class ApkBuildTimeValueSource : ValueSource<String, ValueSourceParameters.None> {
    override fun obtain(): String {
        return getApkBuildTime()
    }
}

private fun getApkBuildTime(): String {
    return getFormattedTime(pattern = "yyyyMMdd_HHmmss")
}

private fun getFormattedTime(pattern: String): String {
    val now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return now.format(formatter)
}
