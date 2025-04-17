package ru.chaglovne.l2.components.counters.ui_logic

import com.arkivanov.decompose.value.Value

interface CounterComponent {
    val model: Value<Model>

    fun increment()
    fun decrement()
    fun setTextValue(value: String)

    data class Model(
        val count: Int
    ) {
        val isCanDecrement: Boolean = count > 0
        val isCanIncrement: Boolean = count < Int.MAX_VALUE
    }
}