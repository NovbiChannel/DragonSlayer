package ru.chaglovne.l2.components.counters.ui_logic

import DEFAULT_DELAY
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update

class DefaultCounterComponent(): CounterComponent {
    private val _model =
        MutableValue(
            CounterComponent.Model(DEFAULT_DELAY.toInt())
        )
    override val model: Value<CounterComponent.Model> = _model

    override fun increment() {
        _model.update { it.copy(count = it.count + 1) }
    }

    override fun decrement() {
        _model.update { it.copy(count = it.count - 1) }
    }
}