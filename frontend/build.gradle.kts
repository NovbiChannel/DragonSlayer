import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val majore = 1
val minore = 0
val path = 0

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.jNativeHook)
            implementation(kotlin("stdlib"))
            implementation(projects.coreModule)
            implementation(projects.apiModule)
            implementation(projects.commonModule)
            implementation(projects.databaseModule)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "ru.chaglovne.l2.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "ru.chaglovne.l2"
            packageVersion = "$majore.$minore.$path"
        }
    }
}
compose.resources {
    generateResClass = always
}