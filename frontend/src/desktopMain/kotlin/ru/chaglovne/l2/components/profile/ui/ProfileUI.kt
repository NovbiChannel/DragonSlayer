package ru.chaglovne.l2.components.profile.ui

import androidx.compose.runtime.Composable
import ru.chaglovne.l2.components.profile.ui_logic.ProfileComponent
import ru.chaglovne.l2.compose_ui.AccentButton

@Composable
fun ProfileContent(component: ProfileComponent) {
    AccentButton(
        title = "Войти с VK ID",
        isSelected = true
    ) {
        component.openUrlInBrowser("https://openjfx.io/")
    }
}