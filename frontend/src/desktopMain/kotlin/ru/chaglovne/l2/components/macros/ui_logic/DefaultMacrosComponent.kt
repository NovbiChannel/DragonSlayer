package ru.chaglovne.l2.components.macros.ui_logic

import EventManager
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import events.DatabaseEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.chaglovne.l2.database.DatabaseManager

class DefaultMacrosComponent(
    componentContext: ComponentContext,
    private val databaseManager: DatabaseManager,
    private val onOpenEditor: () -> Unit
): MacrosComponent, ComponentContext by componentContext {
    private val _model = MutableValue(
        MacrosComponent.Model(
            macros = databaseManager.getMacros()
        )
    )
    private var eventJob: Job? = null

    init {
        eventJob = CoroutineScope(Dispatchers.IO).launch {
            EventManager.eventsFlow.collect { event ->
                when (event) {
                    is DatabaseEvents.NewRecord -> updateMacros(event.macroId)
                }
            }
        }
    }
    override val model: Value<MacrosComponent.Model> = _model
    override fun onOpenEditor() = onOpenEditor.invoke()
    private fun updateMacros(receiveId: Int) {
        val newMacro = databaseManager.getMacro(receiveId)
        newMacro?.let { _model.update { it.copy(macros = _model.value.macros + newMacro) } }
    }
}