package ru.chaglovne.l2.components.editor.ui_logic

import DEFAULT_DELAY
import LoopType
import TimeUnit
import com.arkivanov.decompose.value.Value
import ru.chaglovne.l2.components.input.ui_logic.TextInputComponent

interface EditorComponent {
    val model: Value<Model>

    fun setLoopType(loopType: LoopType)
    fun onAddEvent(type: EventType)
    fun outputHandler(output: Output)

    val textInputComponent: TextInputComponent

    data class Model(
        val loopType: LoopType,
        val events: List<Event>,
        val selectedEventId: Int,
        val title: String
    )

    data class Event(
        val id: Int,
        val eventType: EventType,
        val title: String
    )
    sealed class EventType {
        data class Delay(val timeUnit: TimeUnit = TimeUnit.Millisecond(DEFAULT_DELAY.toInt())): EventType()
        data class KeyPress(val key: Int, var timeUnit: TimeUnit? = null): EventType()
        data class KeyRelease(val key: Int): EventType()
        data class MousePress(val key: Int, var timeUnit: TimeUnit? = null): EventType()
        data class MouseRelease(val key: Int): EventType()
    }

    sealed class Output {
        data object Clear : Output()
        data class Delete(val eventId: Int): Output()
        data class MoveUp(val eventId: Int): Output()
        data class MoveDown(val eventId: Int): Output()
        data class Select(val eventId: Int): Output()
        data class SetDelay(val eventId: Int, val timeUnit: TimeUnit): Output()
        data class SetInterval(val eventId: Int, val timeUnit: TimeUnit): Output()
    }
}