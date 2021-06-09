import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.java))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}