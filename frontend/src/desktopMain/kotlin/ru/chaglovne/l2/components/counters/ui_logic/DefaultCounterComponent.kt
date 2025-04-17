package ru.chaglovne.l2.components.counters.ui_logic

import DEFAULT_DELAY
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update

class DefaultCounterComponent(count: Int = DEFAULT_DELAY.toInt()): CounterComponent {
    private val _model =
        MutableValue(
            CounterComponent.Model(count)
        )
    override val model: Value<CounterComponent.Model> = _model

    override fun increment() {
        _model.update { it.copy(count = it.count + 1) }
    }

    override fun decrement() {
        _model.update { it.copy(count = it.count - 1) }
    }

    override fun setTextValue(value: String) {
        val currentValue = validateValue(value)
        _model.value = CounterComponent.Model(currentValue)
    }

    private fun validateValue(value: String): Int {
        if (value.isBlank()) return 0
        val intValue = value.toInt()
        if (intValue < 0) throw IllegalArgumentException("The value cannot be less than zero")
        return intValue
    }
}