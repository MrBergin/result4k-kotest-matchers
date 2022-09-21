plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.dokka.gradle.plugin)
}
