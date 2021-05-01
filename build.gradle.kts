import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.4.30"
    id("java-conventions")
}

description = "Kotest matchers for Result4K, the friendly Kotlin Result type"

group = "dev.mrbergin"
val releaseVersion: String? by project
version = releaseVersion ?: "LOCAL"

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("io.kotest:kotest-assertions-core-jvm:4.4.3")
    api("dev.forkhandles:result4k")

    implementation(platform("dev.forkhandles:forkhandles-bom:1.8.5.0"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testImplementation("com.natpryce:hamkrest:1.8.0.1")
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

publishing {
    repositories {
        maven("https://maven.pkg.github.com/MrBergin/result4k-kotest-matchers") {
            name = "Github"
            credentials {
                username = System.getenv("GithubUser")
                password = System.getenv("GithubPassword")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set(artifactId)
                description.set(project.description ?: "")
                developers {
                    developer {
                        name.set("Jordan Bergin")
                        name.set("jordan.j.bergin@protonmail.com")
                    }
                }
            }
        }
    }
}