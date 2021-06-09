plugins {
    kotlin("jvm")
    `conventions-java`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(platform(Platforms.forkhandlesBom))
    implementation(Libs.result4k)

    testImplementation("dev.mrbergin:result4k-kotest-matchers:LOCAL")
    testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotest}")
}
