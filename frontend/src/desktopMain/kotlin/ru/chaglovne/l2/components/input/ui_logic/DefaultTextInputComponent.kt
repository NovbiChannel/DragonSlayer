package ru.chaglovne.l2.components.input.ui_logic

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update

class DefaultTextInputComponent(
    componentContext: ComponentContext,
    placeholder: String,
    initText: String? = null,
    private val output: (String) -> Unit
): TextInputComponent, ComponentContext by componentContext {
    private val _model = MutableValue(TextInputComponent.Model(placeholder = placeholder, input = initText?: ""))
    override val model: Value<TextInputComponent.Model> = _model

    override fun onInputChanged(input: String) {
        _model.update { it.copy(input = input, isError = false) }
        output(input)
    }

    override fun onError() { _model.update { it.copy(isError = true) } }
}