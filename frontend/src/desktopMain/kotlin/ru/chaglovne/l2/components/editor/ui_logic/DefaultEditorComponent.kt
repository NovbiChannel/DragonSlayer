package ru.chaglovne.l2.components.editor.ui_logic

import DEFAULT_DELAY
import EventManager
import EventType
import InputType
import LoopType
import Macro
import MouseKeyCodes
import TimeUnit
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.chaglovne.l2.components.input.ui_logic.DefaultTextInputComponent
import ru.chaglovne.l2.components.input.ui_logic.TextInputComponent
import ru.chaglovne.l2.database.DatabaseManager

class DefaultEditorComponent(
    componentContext: ComponentContext,
    private val database: DatabaseManager,
    private val macro: Macro? = null
): EditorComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val _model =
        MutableValue(
            EditorComponent.Model(
                loopType = macro?.loopType?: LoopType.INFINITE,
                events = macro?.events?.mapIndexed { index, eventType ->  EditorComponent.Event(
                    id = index,
                    eventType = eventType,
                    title = generateTitle(eventType)
                ) }?: emptyList(),
                selectedEventId = macro?.events?.lastIndex?: -1 ,
                title = macro?.title?: ""
            )
        )
    override val model: Value<EditorComponent.Model> = _model

    override fun setLoopType(loopType: LoopType) {
        _model.update { it.copy(loopType = loopType) }
    }

    override fun outputHandler(output: EditorComponent.Output) {
        when (output) {
            is EditorComponent.Output.Delete -> { onDeleteEvent(output.eventId) }
            is EditorComponent.Output.MoveDown -> { onMoveEventDown(output.eventId) }
            is EditorComponent.Output.MoveUp -> { onMoveEventUp(output.eventId) }
            is EditorComponent.Output.Select -> { onEventSelect(output.eventId) }
            is EditorComponent.Output.SetDelay -> { onDelayChange(eventId = output.eventId, timeUnit = output.timeUnit) }
            is EditorComponent.Output.SetInterval -> { onIntervalChange(eventId = output.eventId, timeUnit = output.timeUnit) }
            is EditorComponent.Output.Clear -> { onClear() }
            is EditorComponent.Output.SaveData -> { onSaveChange(output.type) }
        }
    }

    override fun setMacro(macro: Macro) {
        _model.update { it.copy(
            loopType = macro.loopType,
            events = macro.events.mapIndexed { index, eventType -> EditorComponent.Event(
                id = index,
                eventType = eventType,
                title = generateTitle(eventType)
            ) },
            selectedEventId = macro.events.lastIndex,
            title = it.title
        ) }
    }

    override val textInputComponent: TextInputComponent =
        DefaultTextInputComponent(componentContext, "Введите наименование макроса", initText = macro?.title) { inputChange ->
            _model.update { it.copy(title = inputChange) }
        }

    override fun onAddEvent(type: EventType) {
        val defaultDelayTitle = "Задержка $DEFAULT_DELAY" + TimeUnit.Millisecond(DEFAULT_DELAY.toInt()).getName()

        fun createDelayEvent(id: Int) = EditorComponent.Event(
            id = id,
            eventType = EventType.Delay(),
            title = defaultDelayTitle
        )

        fun createEvent(id: Int, eventType: EventType, title: String) = EditorComponent.Event(
            id = id,
            eventType = eventType,
            title = title
        )

        _model.update {
            val id = it.events.lastIndex + 1
            val events = when (type) {
                is EventType.Delay -> listOf(createEvent(id, type, generateTitle(type)))
                else -> listOf(
                    createEvent(id, type, generateTitle(type)),
                    createDelayEvent(id + 1)
                )
            }
            val updatedEvents =
                it.events + events
            it.copy(
                events = updatedEvents,
                selectedEventId = updatedEvents.last().id
            )
        }
    }

    private fun generateTitle(eventType: EventType): String {
        return when(eventType) {
            is EventType.Delay -> "Задержка " +
                    eventType.timeUnit.delay() +
                    eventType.timeUnit.getName()
            is EventType.KeyPress -> "Нажать клавишу " +
                    NativeKeyEvent.getKeyText(eventType.key) +
                    generateTimeUnitOptions(eventType.timeUnit)
            is EventType.KeyRelease -> "Отпустить клавишу " +
                    NativeKeyEvent.getKeyText(eventType.key)
            is EventType.MousePress -> "Нажать кнопку мыши " +
                    MouseKeyCodes.getKeyName(eventType.key) +
                    generateTimeUnitOptions(eventType.timeUnit)
            is EventType.MouseRelease -> "Отпустить кнопку мыши " +
                    MouseKeyCodes.getKeyName(eventType.key)
        }
    }

    private fun generateTimeUnitOptions(timeUnit: TimeUnit?): String {
        return timeUnit?.let {
            if (timeUnit.value > 0) {
                ", Интервал нажатия " + timeUnit.value + timeUnit.getName()
            } else { "" }
        }?: ""
    }

    private fun onDeleteEvent(eventId: Int) {
        val event = _model.value.events.find { it.id == eventId }
        event?.let {
            _model.update {
                val newList = it.events.toMutableList()
                newList.remove(event)
                it.copy(events = newList.toList())
            }
        }
    }

    private fun onMoveEventUp(eventId: Int) {
        _model.update { model ->
            val events = model.events.toMutableList()
            val index = events.indexOfFirst { it.id == eventId }

            if (index > 0) {
                val eventToMove = events[index]
                events[index] = events[index - 1]
                events[index - 1] = eventToMove
            }

            model.copy(events = events)
        }
    }

    private fun onMoveEventDown(eventId: Int) {
        _model.update { model ->
            val events = model.events.toMutableList()
            val index = events.indexOfFirst { it.id == eventId }

            if (index < events.size - 1) {
                val eventToMove = events[index]
                events[index] = events[index + 1]
                events[index + 1] = eventToMove
            }

            model.copy(events = events)
        }
    }

    private fun onEventSelect(eventId: Int) {
        _model.update { it.copy(selectedEventId = eventId) }
    }

    private fun onDelayChange(eventId: Int, timeUnit: TimeUnit) {
        val event = _model.value.events.find { it.id == eventId }
        event?.let { existingEvent  ->
            when (val type = existingEvent.eventType) {
                is EventType.Delay -> {
                    val updatedType = type.copy(timeUnit = timeUnit)
                    updateEvent(eventId) { event ->
                        event.copy(
                            eventType = updatedType,
                            title = "Задержка ${timeUnit.value}" + timeUnit.getName()
                        )
                    }
                }
                else -> return
            }
        }
    }

    private fun onIntervalChange(eventId: Int, timeUnit: TimeUnit) {
        val event = _model.value.events.find { it.id == eventId }
        event?.let { existingEvent ->
            when (val type = existingEvent.eventType) {
                is EventType.KeyPress -> {
                    val updatedType = type.copy(timeUnit = timeUnit)
                    updateEvent(eventId) { event ->
                        var title = "Нажать клавишу ${NativeKeyEvent.getKeyText(type.key)}"
                        if (timeUnit.value > 0) {
                            title = title + ", Интервал нажатия ${timeUnit.value}" + timeUnit.getName()
                        }
                        event.copy(
                            eventType = updatedType,
                            title = title
                        )
                    }
                }
                is EventType.MousePress -> {
                    val updatedType = type.copy(timeUnit = timeUnit)
                    updateEvent(eventId) { event ->
                        var title = "Нажать кнопку мыши ${MouseKeyCodes.getKeyName(type.key)}"
                        if (timeUnit.value > 0) {
                            title = title + ", Интервал нажатия ${timeUnit.value}" + timeUnit.getName()
                        }
                        event.copy(
                            eventType = updatedType,
                            title = title
                        )
                    }
                }
                else -> return
            }
        }
    }

    private fun onSaveChange(inputType: InputType) {
        val macroData = _model.value.toMacro(inputType)
        val message: String

        val isNewMacro = macro == null
        val isAvailable = if (isNewMacro) {
            database.checkAvailabilityInput(inputType)
        } else {
            database.checkAvailabilityInput(inputType, macro?.id)
        }

        if (isAvailable) {
            val id = if (isNewMacro) {
                database.addMacro(macroData).also {
                    message = "Макрос успешно сохранен"
                }
            } else {
                database.addMacro(macroData).also {
                    message = "Макрос успешно изменен"
                }
            }

            scope.launch {
                if (isNewMacro) {
                    EventManager.newRecordEvent(id)
                } else {
                    EventManager.updateEvent(id)
                }
                EventManager.sendMessage(message)
            }

            if (isNewMacro) {
                onClear()
            }
        } else {
            message = "Упс... Кажется ты уже используешь эту кнопку для другого макроса"
            scope.launch { EventManager.sendMessage(message) }
        }
    }

    private fun onClear() {
        _model.update { it.copy(events = emptyList(), selectedEventId = -1) }
        textInputComponent.onInputChanged("")
    }

    private fun updateEvent(eventId: Int, update: (EditorComponent.Event) -> EditorComponent.Event) {
        _model.update { model ->
            val updatedEvents = model.events.map { existingEvent ->
                if (existingEvent.id == eventId) {
                    update(existingEvent)
                } else {
                    existingEvent
                }
            }
            model.copy(events = updatedEvents)
        }
    }
    private fun EditorComponent.Model.toMacro(inputType: InputType): Macro =
        Macro(
            title = this.title,
            isPublic = false,
            description = null,
            inputType = inputType,
            loopType = this.loopType,
            events = this.events.map { it.eventType }
        )
}