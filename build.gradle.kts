plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktlint)
}

kotlin {
    jvmToolchain(21)
}