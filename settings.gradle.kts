@file:Suppress("UnstableApiUsage")

import org.gradle.toolchains.foojay.FoojayToolchainResolver

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver") version "1.0.0"
}

toolchainManagement {
    jvm {
        javaRepositories {
            repository("foojay") {
                resolverClass.set(FoojayToolchainResolver::class.java)
            }
        }
    }
}

rootProject.name = "advent-of-code"