package ru.chaglovne.l2

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Dragon Slayer"
    ) {
        Text("Test start")
    }
}