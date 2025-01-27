package ru.chaglovne.l2.components.macros.ui_logic

import Macro
import com.arkivanov.decompose.value.Value

interface MacrosComponent {
    val model: Value<Model>

    fun onOpenEditor(macro: Macro? = null)
    fun onDeleteMacro(macroId: Int)

    data class Model(
        val macros: List<Macro>
    )
}