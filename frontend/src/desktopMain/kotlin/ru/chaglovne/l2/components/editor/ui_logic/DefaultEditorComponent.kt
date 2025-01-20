package ru.chaglovne.l2.components.editor.ui_logic

import DEFAULT_DELAY
import LoopType
import TimeUnit
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
            is EditorComponent.Output.SetDelay -> { onDelayChange(eventId = output.eventId, timeUnit = output.timeUnit) }
            is EditorComponent.Output.SetInterval -> { onIntervalChange(eventId = output.eventId, timeUnit = output.timeUnit) }
        }
    }

    override fun onAddEvent(type: EditorComponent.EventType) {
        val defaultDelayTitle = "Задержка $DEFAULT_DELAY" + TimeUnit.Millisecond(DEFAULT_DELAY.toInt()).getName()

        fun createDelayEvent(id: Int) = EditorComponent.Event(
            id = id,
            eventType = EditorComponent.EventType.Delay(),
            title = defaultDelayTitle
        )

        fun createEvent(id: Int, eventType: EditorComponent.EventType, title: String) = EditorComponent.Event(
            id = id,
            eventType = eventType,
            title = title
        )

        _model.update {
            val id = it.events.lastIndex + 1
            val events = when (type) {
                is EditorComponent.EventType.Delay -> listOf(
                    createEvent(id, type, "Задержка ${type.timeUnit.delay()}" + type.timeUnit.getName())
                )
                is EditorComponent.EventType.KeyPress -> listOf(
                    createEvent(id, type, "Нажать клавишу ${KeyEvent.getKeyText(type.key)}"),
                    createDelayEvent(id + 1)
                )
                is EditorComponent.EventType.KeyRelease -> listOf(
                    createEvent(id, type, "Отпустить клавишу ${KeyEvent.getKeyText(type.key)}"),
                    createDelayEvent(id + 1)
                )
                is EditorComponent.EventType.MousePress -> listOf(
                    createEvent(id, type, "Нажать кнопку мыши ${MouseEvent.getMouseModifiersText(type.key)}"),
                    createDelayEvent(id + 1)
                )
                is EditorComponent.EventType.MouseRelease -> listOf(
                    createEvent(id, type, "Отпустить кнопку мыши ${MouseEvent.getMouseModifiersText(type.key)}"),
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
                is EditorComponent.EventType.Delay -> {
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
                is EditorComponent.EventType.KeyPress -> {
                    val updatedType = type.copy(timeUnit = timeUnit)
                    updateEvent(eventId) { event ->
                        event.copy(
                            eventType = updatedType,
                            title = event.title + ", Интервал нажатия ${timeUnit.delay()}" + timeUnit.getName()
                        )
                    }
                }
                else -> return
            }
        }
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
}