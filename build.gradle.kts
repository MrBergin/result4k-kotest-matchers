import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
}

description = "Kotest matchers for Result4K, the friendly Kotlin Result type"

group = "mr.bergin"
version = "LOCAL"

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
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            configurePom {
                name(artifactId)
                description(description ?: "")
                developers {
                    developer {
                        name("Jordan Bergin")
                        email("jordan.j.bergin@protonmail.com")
                    }
                }
            }
        }
    }
}