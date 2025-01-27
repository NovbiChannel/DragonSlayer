package ru.chaglovne.l2.components.root.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.launch
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
    val scope = rememberCoroutineScope()
    val stack by component.stack.subscribeAsState()
    val model by component.model.subscribeAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        scope.launch {
            EventManager.msgEventsFlow.collect { msg ->
                snackbarHostState.showSnackbar(msg)
            }
        }
    }

    Row(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
            .padding(16.dp)
    ) {
        SideMenu(
            modifier = Modifier.weight(1f),
            snackbarHostState = snackbarHostState,
            outputHandler = component::outputHandler,
            model = model
        )
        Spacer(Modifier.width(16.dp))
        ContentView(Modifier.weight(3f)) { modifier ->
            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                backgroundColor = Color.Transparent
            ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            color = Colors.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
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