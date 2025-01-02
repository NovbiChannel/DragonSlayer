package ru.chaglovne.l2

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ru.chaglovne.l2.components.root.ui.RootContent
import ru.chaglovne.l2.components.root.ui_logic.DefaultRootComponent

fun main() {
    val lifecycle = LifecycleRegistry()
    application {
        val root = remember { DefaultRootComponent(DefaultComponentContext(lifecycle)) }
        val windowState = rememberWindowState(width = 1000.dp)
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            resizable = false,
            title = "Dragon Slayer"
        ) {
            RootContent(root)
        }
    }
}