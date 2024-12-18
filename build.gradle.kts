plugins {
    alias(libs.plugins.kotlin.jvm)
}
val majore = 0
val minore = 0
val path = 0

version = "$majore.$minore.$path"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jNativeHook)
}
kotlin {
    jvmToolchain(21)
}