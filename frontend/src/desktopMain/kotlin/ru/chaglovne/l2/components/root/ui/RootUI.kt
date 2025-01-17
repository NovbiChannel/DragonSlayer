package ru.chaglovne.l2.components.root.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.chaglovne.l2.components.editor.ui.EditorContent
import ru.chaglovne.l2.components.macros.ui.MacrosContent
import ru.chaglovne.l2.components.profile.ui.ProfileContent
import ru.chaglovne.l2.components.root.ui_logic.RootComponent
import ru.chaglovne.l2.components.settings.ui.SettingsContent
import ru.chaglovne.l2.compose_ui.ContentView
import ru.chaglovne.l2.compose_ui.SideMenu
import ru.chaglovne.l2.theme.Colors

@Composable
fun RootContent(component: RootComponent) {
    val stack by component.stack.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    Row(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
            .padding(16.dp)
    ) {
        SideMenu(
            modifier = Modifier.weight(1f),
            snackbarHostState = snackbarHostState,
            onMacroClick = { component.onMacrosTabClicked() },
            onEditorClick = { component.onEditorTabClicked() },
            onSettingsClick = { component.onSettingsTabClicked() },
            onProfileClick = { component.onProfileTabClicked() },
            onMacroStartStop = { isLaunched -> component.onMacroStartStop(isLaunched) }
        )
        Spacer(Modifier.width(16.dp))
        ContentView(Modifier.weight(3f)) { modifier ->
            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                backgroundColor = Color.Transparent
            ) {
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
                        is RootComponent.Child.ProfileChild -> ProfileContent(child.component)
                    }
                }
            }
        }
    }
}