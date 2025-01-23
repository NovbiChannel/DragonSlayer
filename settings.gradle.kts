rootProject.name = "L2Macros"
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
include(":core")
include(":api")
include(":frontend")
include(":common")
include(":webCallback")
include(":database")

findProject(":core")?.name = "core-module"
findProject(":api")?.name = "api-module"
findProject(":common")?.name = "common-module"
findProject(":database")?.name = "database-module"
