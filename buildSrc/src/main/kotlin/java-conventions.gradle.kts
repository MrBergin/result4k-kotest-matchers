plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
        vendor.set(JvmVendorSpec.AMAZON)
    }
}