plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.javafx)
    application
    java
}

dependencies {
    implementation("org.openjfx:javafx-base:17.0.2")
    implementation("org.openjfx:javafx-controls:17.0.2")
    implementation("org.openjfx:javafx-graphics:17.0.2")
    implementation("org.openjfx:javafx-fxml:17.0.2")
    implementation("org.openjfx:javafx-web:17.0.2")
    implementation(libs.coroutines)
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.base", "javafx.controls", "javafx.graphics", "javafx.fxml", "javafx.web")
}

application {
    mainClass.set("ru.dragonslayer.webview.WebViewAppKt")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf(
        "--module-path", classpath.asPath,
        "--add-modules", "javafx.controls,javafx.fxml,javafx.web,javafx.graphics"
    )
}