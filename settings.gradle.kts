rootProject.name = "DragonSlayerFrontend"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(
    ":core",
    ":api",
    ":frontend",
    ":common",
    ":webCallback",
    ":database",
)

findProject(":core")?.name = "core-module"
findProject(":api")?.name = "api-module"
findProject(":common")?.name = "common-module"
findProject(":database")?.name = "database-module"
include("firebase-wrapper")
