@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
    id("java-conventions")
}

description = "Kotest matchers for Result4K, the friendly Kotlin Result type"

group = "dev.mrbergin"
val releaseVersion: String? by project
version = releaseVersion ?: "LOCAL"

dependencies {
    api(platform(libs.forkhandlesBom))

    api(kotlin("stdlib-jdk8"))
    api(libs.kotestAssertionsCoreJvm)
    api(libs.result4k)

    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.hamkrest)
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

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

val ossrhUsername : String? by project
val ossrhPassword : String? by project

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
    sign(publishing.publications["maven"])
}