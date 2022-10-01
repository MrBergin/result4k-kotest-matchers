rootProject.name = "result4k-kotest-matchers"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

include(":samples")
includeBuild("build-src")



enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

