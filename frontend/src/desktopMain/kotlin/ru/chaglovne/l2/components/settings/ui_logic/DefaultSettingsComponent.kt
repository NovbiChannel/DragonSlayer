package ru.chaglovne.l2.components.settings.ui_logic

import com.arkivanov.decompose.ComponentContext

class DefaultSettingsComponent(
    componentContext: ComponentContext
): SettingsComponent, ComponentContext by componentContext {
}