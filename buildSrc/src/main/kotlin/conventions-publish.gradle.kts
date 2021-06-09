import org.gradle.kotlin.dsl.java

plugins {
    java
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}

val releaseVersion: String? by project

val ossrhUsername : String? by project
val ossrhPassword : String? by project

val signingKey: String? by project
val signingKeyId: String? by project
val signingPassword: String? by project

group = "dev.mrbergin"
version = releaseVersion ?: "LOCAL"

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
    repositories {
        maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
            name = "SonatypeStaging"
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
            name = "SonatypeSnapshot"
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
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

signing {
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications["maven"])
}