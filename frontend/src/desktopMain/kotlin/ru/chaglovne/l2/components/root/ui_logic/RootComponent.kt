package ru.chaglovne.l2.components.root.ui_logic

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import ru.chaglovne.l2.components.editor.ui_logic.EditorComponent
import ru.chaglovne.l2.components.macros.ui_logic.MacrosComponent
import ru.chaglovne.l2.components.profile.ui_logic.ProfileComponent
import ru.chaglovne.l2.components.settings.ui_logic.SettingsComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    val model: Value<Model>

    fun onBackClicked()
    fun onBackClicked(toIndex: Int)
    fun outputHandler(output: Output)

    sealed class Child {
        class MacroChild(val component: MacrosComponent): Child()
        class EditorChild(val component: EditorComponent): Child()
        class SettingsChild(val component: SettingsComponent): Child()
        class ProfileChild(val component: ProfileComponent): Child()
    }

    sealed class Output {
        data object MacroClick: Output()
        data object EditorClick: Output()
        data object ProfileClick: Output()
        data class MacroStartStop(val isLaunched: Boolean, val scope: CoroutineScope): Output()
    }

    data class Model(
        val isMacroSelected: Boolean,
        val isEditorSelected: Boolean,
        val isProfileSelected: Boolean
    )
}