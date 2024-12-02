plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktlint)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
}