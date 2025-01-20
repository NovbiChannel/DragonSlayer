plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

repositories {
    mavenCentral()
}

kotlin {
    jvm("desktop")
    js(IR)
    sourceSets {
        val desktopMain by getting
        val jsMain by getting

        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(projects.commonModule)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}