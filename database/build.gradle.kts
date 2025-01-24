plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

repositories {
    mavenCentral()
}

kotlin {
    jvm("desktop")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.exposed.core)
            implementation(libs.exposed.dao)
            implementation(libs.exposed.jdbc)
            implementation(libs.sqlite.jdbc)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(projects.commonModule)
        }
    }
}