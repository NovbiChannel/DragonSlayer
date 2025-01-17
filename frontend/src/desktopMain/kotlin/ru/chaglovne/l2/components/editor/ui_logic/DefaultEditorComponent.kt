package ru.chaglovne.l2.components.editor.ui_logic

import DEFAULT_DELAY
import LoopType
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

class DefaultEditorComponent(
    componentContext: ComponentContext
): EditorComponent, ComponentContext by componentContext {
    private val _model =
        MutableValue(
            EditorComponent.Model(
                loopType = LoopType.INFINITE,
                events = emptyList(),
                selectedEventId = -1
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
            is EditorComponent.Output.SetDelay -> { onDelayChange(eventId = output.eventId, delay = output.delay) }
        }
    }

    override fun onAddEvent(type: EditorComponent.EventType) {
        _model.update {
            val id = it.events.lastIndex + 1
            val events = when (type) {
                is EditorComponent.EventType.Delay -> listOf(
                    EditorComponent.Event(
                        id = id,
                        eventType = type,
                        title = "Задержка ${type.delay}" + "мс"
                    )
                )
                is EditorComponent.EventType.KeyboardClick -> listOf(
                    EditorComponent.Event(
                        id = id,
                        eventType = type,
                        title = "Нажать клавишу ${KeyEvent.getKeyText(type.key)}"
                    ),
                    EditorComponent.Event(
                        id = id + 1,
                        eventType = EditorComponent.EventType.Delay(),
                        title = "Задержка $DEFAULT_DELAY" + "мс"
                    )
                )
                is EditorComponent.EventType.MouseClick -> listOf(
                    EditorComponent.Event(
                        id = id,
                        eventType = type,
                        title = "Нажать кнопку мыши ${MouseEvent.getMouseModifiersText(type.key)}"
                    ),
                    EditorComponent.Event(
                        id = id + 1,
                        eventType = EditorComponent.EventType.Delay(),
                        title = "Задержка $DEFAULT_DELAY" + "мс"
                    )
                )
            }
            val updatedEvents =
                it.events + events
            it.copy(events = updatedEvents)
        }
    }

    private fun onDeleteEvent(eventId: Int) {
        val event = _model.value.events.find { it.id == eventId }
        event?.let { event ->
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
        _model.update { model ->
            model.copy(selectedEventId = eventId)
        }
    }

    private fun onDelayChange(eventId: Int, delay: Long) {
        val event = _model.value.events.find { it.id == eventId }
        event?.let { event ->
            when (val type = event.eventType) {
                is EditorComponent.EventType.Delay -> {
                    val updatedType = type.copy(delay = delay)

                    _model.update {
                        val updatedEvents = _model.value.events.map { existingEvents ->
                            if (existingEvents.id == eventId) {
                                existingEvents.copy(eventType = updatedType, title = "Задержка ${updatedType.delay}" + "мс")
                            } else {
                                existingEvents
                            }
                        }
                        it.copy(events = updatedEvents)
                    }
                }
                else -> return
            }
        }
    }
}