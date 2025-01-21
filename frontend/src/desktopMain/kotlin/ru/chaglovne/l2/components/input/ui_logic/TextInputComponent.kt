package ru.chaglovne.l2.components.input.ui_logic

import com.arkivanov.decompose.value.Value

interface TextInputComponent {
    val model: Value<Model>
    fun onInputChanged(input: String)
    fun onError()

    data class Model(var input: String = "", val placeholder: String, val isError: Boolean = false)
}