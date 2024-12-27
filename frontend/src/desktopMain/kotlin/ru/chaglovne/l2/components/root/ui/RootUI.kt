package ru.chaglovne.l2.components.root.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.chaglovne.l2.components.editor.ui.EditorContent
import ru.chaglovne.l2.components.macros.ui.MacrosContent
import ru.chaglovne.l2.components.root.ui_logic.RootComponent
import ru.chaglovne.l2.components.settings.ui.SettingsContent
import ru.chaglovne.l2.theme.Colors
import ru.chaglovne.l2.compose_ui.ContentView
import ru.chaglovne.l2.compose_ui.SideMenu

@Composable
fun RootContent(component: RootComponent) {
    val stack by component.stack.subscribeAsState()
    Row(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
            .padding(16.dp)
    ) {
        SideMenu(
            modifier = Modifier.weight(1f),
            onMacroClick = { component.onMacrosTabClicked() },
            onEditorClick = { component.onEditorTabClicked() },
            onSettingsClick = { component.onSettingsTabClicked() }
        )
        Spacer(Modifier.width(16.dp))
        ContentView(Modifier.weight(3f)) { modifier ->
            Box(modifier
                .fillMaxSize()
                .background(
                    color = Colors.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                )) {
                when (val child = stack.active.instance) {
                    is RootComponent.Child.EditorChild -> EditorContent(child.component)
                    is RootComponent.Child.MacroChild -> MacrosContent(child.component)
                    is RootComponent.Child.SettingsChild -> SettingsContent(child.component)
                }
            }
        }
    }
}