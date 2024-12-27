package ru.chaglovne.l2

import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ru.chaglovne.l2.components.root.ui.RootContent
import ru.chaglovne.l2.components.root.ui_logic.DefaultRootComponent
import ru.chaglovne.l2.ui.App

fun main() {
    val lifecycle = LifecycleRegistry()
    application {
        val root = remember { DefaultRootComponent(DefaultComponentContext(lifecycle)) }
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Dragon Slayer"
        ) {
            RootContent(root)
        }
    }
}