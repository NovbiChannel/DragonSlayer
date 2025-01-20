package ru.chaglovne.l2.components.counters.ui_logic

import DEFAULT_DELAY
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update

class DefaultCounterComponent(val count: Int = DEFAULT_DELAY.toInt()): CounterComponent {
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
}