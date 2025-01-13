package ru.chaglovne.l2.components.root.ui_logic

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.chaglovne.l2.components.editor.ui_logic.EditorComponent
import ru.chaglovne.l2.components.macros.ui_logic.MacrosComponent
import ru.chaglovne.l2.components.profile.ui_logic.ProfileComponent
import ru.chaglovne.l2.components.settings.ui_logic.SettingsComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onMacrosTabClicked()
    fun onEditorTabClicked()
    fun onSettingsTabClicked()
    fun onProfileTabClicked()
    fun onBackClicked()
    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class MacroChild(val component: MacrosComponent): Child()
        class EditorChild(val component: EditorComponent): Child()
        class SettingsChild(val component: SettingsComponent): Child()
        class ProfileChild(val component: ProfileComponent): Child()
    }
}