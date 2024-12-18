plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.jNativeHook)
    implementation(libs.coroutines)
}