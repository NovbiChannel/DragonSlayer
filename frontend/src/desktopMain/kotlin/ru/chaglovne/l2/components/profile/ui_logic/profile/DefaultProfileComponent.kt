package ru.chaglovne.l2.components.profile.ui_logic.profile

import com.arkivanov.decompose.ComponentContext

class DefaultProfileComponent(
    componentContext: ComponentContext
): ProfileComponent, ComponentContext by componentContext {
}