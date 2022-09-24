plugins {
    java
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
    id("io.github.gradle-nexus.publish-plugin")
}

val releaseVersion: String? by project

val ossrhUsername: String? by project
val ossrhPassword: String? by project

val signingKey: String? by project
val signingKeyId: String? by project
val signingPassword: String? by project

group = "dev.mrbergin"
version = releaseVersion ?: "LOCAL"
description = "Kotest matchers for Result4K, the friendly Kotlin Result type"

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

val sourcesJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(javadocJar)
            artifact(sourcesJar)
            from(components["java"])
            pom {
                name.set(artifactId)
                description.set(project.description ?: "")
                url.set("https://github.com/MrBergin/result4k-kotest-matchers")

                developers {
                    developer {
                        name.set("Jordan Bergin")
                        name.set("contact@mrbergin.dev")
                    }
                }

                scm {
                    url.set("git@github.com/MrBergin/result4k-kotest-matchers.git")
                    connection.set("scm:git:git@github.com/MrBergin/result4k-kotest-matchers.git")
                    developerConnection.set("scm:git:git@github.com/MrBergin/result4k-kotest-matchers.git")
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }

        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(ossrhUsername)
            password.set(ossrhPassword)
        }
    }
}

signing {
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword ?: "")
    sign(publishing.publications["maven"])
}
