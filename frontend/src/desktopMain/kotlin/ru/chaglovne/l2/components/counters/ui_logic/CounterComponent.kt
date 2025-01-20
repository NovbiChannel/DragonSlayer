package ru.chaglovne.l2.components.counters.ui_logic

import com.arkivanov.decompose.value.Value

interface CounterComponent {
    val model: Value<Model>

    fun increment()
    fun decrement()

    data class Model(
        val count: Int
    )
}