package ru.chaglovne.l2.components.editor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.chaglovne.l2.components.editor.ui_logic.EditorComponent
import ru.chaglovne.l2.compose_ui.KeyButton
import ru.chaglovne.l2.compose_ui.KeyButtonModel
import ru.chaglovne.l2.compose_ui.Keyboard
import ru.chaglovne.l2.compose_ui.Mouse
import java.awt.event.KeyEvent

@Composable
fun EditorContent(component: EditorComponent) {
    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(Modifier.weight(1f)) { Text("test") }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Keyboard { keyCode ->
                println(KeyEvent.getKeyText(keyCode))
            }
            Column(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    KeyButton(Modifier, KeyButtonModel("Q", 1)) {}
                    KeyButton(Modifier, KeyButtonModel("W", 1)) {}
                    KeyButton(Modifier, KeyButtonModel("R", 1)) {}
                }
                Mouse { keyCode ->
                    val message = when (keyCode) {
                        1 -> "Left mouse button"
                        2 -> "Right mouse button"
                        4 -> "additional 4 mouse button"
                        5 -> "additional5 mouse button"
                        else -> "Unknown button"
                    }
                    println(message)
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
            ) {
                Text("test")
            }
            Button(
                onClick = {},
            ) {
                Text("clean")
            }
            Button(
                onClick = {},
            ) {
                Text("save")
            }
        }
    }
}