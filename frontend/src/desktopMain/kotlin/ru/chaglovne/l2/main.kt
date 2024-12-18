package ru.chaglovne.l2

import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.chaglovne.l2.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Dragon Slayer",
        onKeyEvent = { key ->
            println(key.key)
            true
        }
    ) {
        App()
    }
}