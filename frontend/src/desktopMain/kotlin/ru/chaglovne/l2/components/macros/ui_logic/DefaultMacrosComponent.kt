package ru.chaglovne.l2.components.macros.ui_logic

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.chaglovne.l2.database.DatabaseManager

class DefaultMacrosComponent(
    componentContext: ComponentContext,
    databaseManager: DatabaseManager,
    private val onOpenEditor: () -> Unit
): MacrosComponent, ComponentContext by componentContext {
    private val _model = MutableValue(
        MacrosComponent.Model(
            macros = databaseManager.getMacros()
        )
    )
    override val model: Value<MacrosComponent.Model> = _model
    override fun onOpenEditor() = onOpenEditor.invoke()
}