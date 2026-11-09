package github.leavesczy.matisse.buildlogic

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import github.leavesczy.matisse.buildlogic.config.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPublishConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(receiver = target) {
            apply(plugin = "com.vanniktech.maven.publish")
            extensions.configure<MavenPublishBaseExtension> {
                publishToMavenCentral()
                // JitPack 执行 publishToMavenLocal 时没有 GPG 密钥
                // 仅向 Maven Central 发布时才需要签名
                if (System.getenv("JITPACK") != "true") {
                    signAllPublications()
                }
                configure(platform = AndroidSingleVariantLibrary())
                coordinates(
                    groupId = "io.github.leavesczy",
                    artifactId = "matisse",
                    version = libs.findVersion("leavesczy-matisse").get().toString()
                )
                pom {
                    name.set("Matisse")
                    description.set("An Android Image and Video Selection Framework Implemented with Jetpack Compose")
                    inceptionYear.set("2022")
                    url.set("https://github.com/leavesczy/Matisse")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                            distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("leavesczy")
                            name.set("leavesczy")
                            url.set("https://github.com/leavesczy")
                        }
                    }
                    scm {
                        url.set("https://github.com/leavesczy/Matisse")
                        connection.set("scm:git:git://github.com/leavesczy/Matisse.git")
                        developerConnection.set("scm:git:ssh://git@github.com/leavesczy/Matisse.git")
                    }
                }
            }
        }
    }

}
