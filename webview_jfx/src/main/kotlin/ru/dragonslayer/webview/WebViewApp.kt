package ru.dragonslayer.webview

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javafx.stage.Stage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

class WebViewApp: Application() {
    private lateinit var url: String
    private val scope = CoroutineScope(SupervisorJob())
    override fun start(primaryStage: Stage) {
        val webView = WebView()
        val webEngine: WebEngine = webView.engine

        webEngine.locationProperty().addListener { _, _, newLocation ->
            scope.launch { WebViewSharedFlow.produceUrl(newLocation) }
        }

        val parameters = parameters.raw
        url = if (parameters.isNotEmpty()) {
            parameters[0]
        } else {
            "https://www.google.ru/"
        }

        webEngine.load(url)

        val scene = Scene(webView, 800.0, 600.0)
        primaryStage.scene = scene
        primaryStage.title = "WebView Example"
        primaryStage.show()
    }
}

fun launchWebView(url: String, scope: CoroutineScope) {
    scope.launch { Application.launch(WebViewApp::class.java, url) }
}

fun main() {
    Application.launch(WebViewApp::class.java)
}