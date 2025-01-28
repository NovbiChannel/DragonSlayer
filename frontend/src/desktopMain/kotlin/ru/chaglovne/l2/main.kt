package ru.chaglovne.l2

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.github.kwhat.jnativehook.GlobalScreen
import ru.chaglovne.l2.components.root.ui.RootContent
import ru.chaglovne.l2.components.root.ui_logic.DefaultRootComponent
import ru.chaglovne.l2.database.DatabaseManager
import java.io.File

fun main() {
    val lifecycle = LifecycleRegistry()
    val db = File("database.db")
    val databaseManager = DatabaseManager(db.absolutePath)

    application {
        val root = remember { DefaultRootComponent(DefaultComponentContext(lifecycle), databaseManager) }
        val windowState = rememberWindowState(width = 1000.dp)
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = {
                GlobalScreen.unregisterNativeHook()
                exitApplication()
            },
            state = windowState,
            resizable = false,
            title = "Dragon Slayer"
        ) {
            RootContent(root)
        }
    }
}