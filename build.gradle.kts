plugins {
    application

    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktlint)
    id("org.graalvm.buildtools.native") version "0.10.4"
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "MainKt"
}

graalvmNative {
    toolchainDetection = true

    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(21))
                vendor.set(JvmVendorSpec.matching("Oracle"))
            })

            buildArgs("-Ob")

            resources.autodetect()
        }
    }
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(kotlin("reflect"))
}