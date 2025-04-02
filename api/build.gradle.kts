plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

repositories {
    mavenCentral()
}

kotlin {
    jvm("desktop")
    js(IR) {
        browser()
    }
    sourceSets {
        val desktopMain by getting
        val desktopTest by getting
        val jsMain by getting

        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.ws)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.coroutines)
            implementation(projects.commonModule)
        }

        desktopTest.dependencies {
            implementation(libs.coroutines)
            implementation(kotlin("test"))
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}