package ru.chaglovne.l2.components.macros.ui_logic

import com.arkivanov.decompose.ComponentContext

class DefaultMacrosComponent(
    componentContext: ComponentContext
): MacrosComponent, ComponentContext by componentContext {

}