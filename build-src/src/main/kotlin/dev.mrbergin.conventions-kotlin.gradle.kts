import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    kotlin("jvm")
}

val libs = the<LibrariesForLibs>()

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}