plugins {
    id("dev.mrbergin.conventions-kotlin")
}


dependencies {
    implementation(platform(libs.forkhandles))
    implementation(libs.result4k)

    testImplementation(project(":"))
    testImplementation(libs.kotest.runner.junit5)
}
