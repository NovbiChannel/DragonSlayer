package ru.chaglovne.l2.components.editor.ui_logic

import com.arkivanov.decompose.ComponentContext

class DefaultEditorComponent(
    componentContext: ComponentContext
): EditorComponent, ComponentContext by componentContext {
}