plugins {
    kotlin("jvm")
    `conventions-java`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(Platforms.forkhandlesBom))
    implementation(Libs.result4k)

    testImplementation(project(":"))
    testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotest}")
}
