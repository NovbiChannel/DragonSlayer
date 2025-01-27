package ru.chaglovne.l2.components.macros.ui_logic

import EventManager
import Macro
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
    private val onOpenEditor: (macro: Macro?) -> Unit
): MacrosComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _model = MutableValue(
        MacrosComponent.Model(
            macros = databaseManager.getAllMacros()
        )
    )
    private var eventJob: Job? = null

    init {
        eventJob = scope.launch(Dispatchers.IO) {
            EventManager.dbEventsFlow.collect { event ->
                when (event) {
                    is DatabaseEvents.NewRecord -> addMacroToList(event.macroId)
                    is DatabaseEvents.Update -> updateMacro(event.macroId)
                    is DatabaseEvents.Delete -> deleteMacro(event.macroId)
                }
            }
        }
    }

    override val model: Value<MacrosComponent.Model> = _model
    override fun onOpenEditor(macro: Macro?) = onOpenEditor.invoke(macro)
    override fun onDeleteMacro(macroId: Int) = deleteMacro(macroId)

    private fun addMacroToList(receiveId: Int) {
        val newMacro = databaseManager.getMacro(receiveId)
        newMacro?.let { _model.update { it.copy(macros = _model.value.macros + newMacro) } }
    }
    private fun updateMacro(receiveId: Int) {
        val newMacro = databaseManager.getMacro(receiveId)
        newMacro?.let {
            _model.value.macros.forEachIndexed { index, macro ->
                if (macro.id == receiveId) {
                    _model.update {
                        val newList = it.macros.toMutableList()
                        newList[index] = newMacro
                        it.copy(macros = newList)
                    }
                }
            }
        }
    }
    private fun deleteMacro(receiveId: Int) {
        val isSuccess = databaseManager.deleteMacro(receiveId)
        if (isSuccess) {
            _model.value.macros.forEachIndexed { index, macro ->
                if (macro.id == receiveId) {
                    _model.update {
                        val newList = it.macros.toMutableList()
                        newList.removeAt(index)
                        it.copy(macros = newList)
                    }
                }
            }
            scope.launch { EventManager.sendMessage("Макрос удален") }
        }
    }
}