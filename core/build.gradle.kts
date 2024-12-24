val majore = 1
val minore = 0
val path = 0

plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

version = "$majore.$minore.$path"

dependencies {
    implementation(libs.jNativeHook)
    implementation(libs.coroutines)
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("MainKt")
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("dragon_slayer")
    archiveVersion.set(version.toString())
    archiveClassifier.set("")

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}