package ru.chaglovne.l2

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.dragon_slayer.firebase.impl.FirebaseAuth
import com.dragonslayer.JsonAppConfig
import com.github.kwhat.jnativehook.GlobalScreen
import dragonslayerfrontend.frontend.generated.resources.Res
import dragonslayerfrontend.frontend.generated.resources.app_logo
import dragonslayerfrontend.frontend.generated.resources.ic_baseline_close
import dragonslayerfrontend.frontend.generated.resources.ic_underline
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.editor.ui.IconButton
import ru.chaglovne.l2.components.root.ui.RootContent
import ru.chaglovne.l2.components.root.ui_logic.DefaultRootComponent
import ru.chaglovne.l2.database.DatabaseManager
import ru.chaglovne.l2.theme.Colors
import java.awt.MouseInfo
import java.awt.Point
import java.io.File

fun main(args: Array<String>) {
    val lifecycle = LifecycleRegistry()
    val configFile = File(args[0])
    val appConfig = JsonAppConfig.fromFile(configFile)
    val databaseManager = DatabaseManager(appConfig)
    val firebaseAuth = FirebaseAuth(appConfig)
    val appName = appConfig.getString("app_name")

    application {
        val root = remember { DefaultRootComponent(DefaultComponentContext(lifecycle), databaseManager, firebaseAuth) }
        val windowState = rememberWindowState(width = 1000.dp)

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = {
                GlobalScreen.unregisterNativeHook()
                exitApplication()
            },
            undecorated = true,
            state = windowState,
            icon = painterResource(Res.drawable.app_logo),
            resizable = false,
            title = appName?: ""
        ) {
            var isDragging by remember { mutableStateOf(false) }
            var dragStartPoint by remember { mutableStateOf(Point(0, 0)) }

            Column(
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Colors.primaryContainer)
                        .pointerInput(true) {
                            detectDragGestures(
                                onDragStart = {
                                    isDragging = true

                                    dragStartPoint = Point(
                                        MouseInfo.getPointerInfo().location.x - window.location.x,
                                        MouseInfo.getPointerInfo().location.y - window.location.y
                                    )
                                },
                                onDragEnd = {
                                    isDragging = false
                                },
                                onDrag = { change, _ ->
                                    if (isDragging) {
                                        val currentMousePoint = MouseInfo.getPointerInfo().location
                                        window.setLocation(
                                            currentMousePoint.x - dragStartPoint.x,
                                            currentMousePoint.y - dragStartPoint.y
                                        )
                                    }
                                    change.consume()
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(Res.drawable.ic_underline) {
                            windowState.isMinimized = true
                        }
                        IconButton(Res.drawable.ic_baseline_close) {
                            exitApplication()
                        }
                    }
                }
                RootContent(root)
            }
        }
    }
}