import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val libs = the<LibrariesForLibs>()

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.kotlin.jvm.build.get()))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = libs.versions.kotlin.jvm.target.get()
    }
}

tasks.withType<JavaCompile> {
    options.release.set(libs.versions.java.release.target.get().toInt())
}
