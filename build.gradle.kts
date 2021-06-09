@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("jvm")
    `conventions-java`
    `conventions-publish`
}

dependencies {
    api(platform(Platforms.forkhandlesBom))

    api(kotlin("stdlib-jdk8"))
    api(Libs.kotestAssertionsCoreJvm)
    api(Libs.result4k)

    testImplementation(Libs.junitJupiterApi)
    testImplementation(Libs.junitJupiterEngine)
    testImplementation(Libs.hamkrest)
    testImplementation(kotlin("test"))
}
