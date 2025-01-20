import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        moduleName = "authCallback"
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(projects.apiModule)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
            }
        }
    }
}

tasks.register<Copy>("assembleWeb") {
    val buildDir = layout.buildDirectory.asFile.get()
    val outputDir = buildDir.resolve("webCallback")

    from("${buildDir}/kotlin-webpack/js/productionExecutable/webCallback.js")
    from("src/jsMain/resources/index.html")
    from("src/jsMain/resources/styles.css")

    into(outputDir)

    group = "build"
    description = "Assembles the web application into a single directory."
}

tasks.named("assembleWeb").dependsOn("jsBrowserProductionWebpack")
