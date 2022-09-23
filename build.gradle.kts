@file:Suppress("UnstableApiUsage")

plugins {
    id("dev.mrbergin.conventions-kotlin")
    id("dev.mrbergin.conventions-publish")
    id("dev.mrbergin.conventions-sast")
}

dependencies {
    api(platform(libs.forkhandles))

    api(kotlin("stdlib-jdk8"))
    api(libs.kotest.assertions.core)
    api(libs.result4k)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.hamkrest)
    testImplementation(kotlin("test"))
}
