package ru.chaglovne.l2.components.editor.ui_logic

import DEFAULT_DELAY
import LoopType
import com.arkivanov.decompose.value.Value

interface EditorComponent {
    val model: Value<Model>

    fun setLoopType(loopType: LoopType)
    fun onAddEvent(type: EventType)
    fun outputHandler(output: Output)

    data class Model(
        val loopType: LoopType,
        val events: List<Event>,
        val selectedEventId: Int
    )

    data class Event(
        val id: Int,
        val eventType: EventType,
        val title: String
    )
    sealed class EventType {
        data class Delay(var delay: Long = DEFAULT_DELAY): EventType()
        class MouseClick(val key: Int): EventType()
        class KeyboardClick(val key: Int): EventType()
    }

    sealed class Output {
        class Delete(val eventId: Int): Output()
        class MoveUp(val eventId: Int): Output()
        class MoveDown(val eventId: Int): Output()
        class Select(val eventId: Int): Output()
        class SetDelay(val eventId: Int, val delay: Long): Output()
    }
}