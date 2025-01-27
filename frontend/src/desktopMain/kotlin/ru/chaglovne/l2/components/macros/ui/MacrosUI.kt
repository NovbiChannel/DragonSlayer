package ru.chaglovne.l2.components.macros.ui

import InputType
import MouseKeyCodes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import ru.chaglovne.l2.components.macros.ui_logic.MacrosComponent
import ru.chaglovne.l2.compose_ui.AccentButton
import ru.chaglovne.l2.compose_ui.InformingDashboard
import ru.chaglovne.l2.compose_ui.MacroItem

@Composable
fun MacrosContent(component: MacrosComponent) {
    val model by component.model.subscribeAsState()

    if (model.macros.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(model.macros) { item ->
                val keyTitle = when (val type = item.inputType) {
                    is InputType.KEYBOARD -> NativeKeyEvent.getKeyText(type.value)
                    is InputType.MOUSE -> MouseKeyCodes.getKeyName(type.value)
                }
                MacroItem(title = item.title, keyTitle = keyTitle) { component.onOpenEditor(item) }
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            InformingDashboard(
                modifier = Modifier.wrapContentSize(),
                imageSize = 100.dp,
                fontSize = 14.sp,
                text = "У вас пока нет макросов.\nЧтобы начать, откройте редактор и \nсоздайте свой первый макрос."
            )
            AccentButton(
                title = "Создать макрос",
                isSelected = true
            ) { component.onOpenEditor() }
        }
    }
}