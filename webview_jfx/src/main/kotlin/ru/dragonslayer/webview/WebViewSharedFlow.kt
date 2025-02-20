package ru.dragonslayer.webview

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object WebViewSharedFlow {
    private val _urlFlow = MutableSharedFlow<String>()
    val urlFlow = _urlFlow.asSharedFlow()

    suspend fun produceUrl(url: String) {
        _urlFlow.emit(url)
    }
}