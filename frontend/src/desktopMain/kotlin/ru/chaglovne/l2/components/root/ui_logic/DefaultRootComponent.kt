package ru.chaglovne.l2.components.root.ui_logic

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.serialization.Serializable
import macroStart
import ru.chaglovne.l2.components.editor.ui_logic.DefaultEditorComponent
import ru.chaglovne.l2.components.macros.ui_logic.DefaultMacrosComponent
import ru.chaglovne.l2.components.profile.ui_logic.DefaultProfileComponent
import ru.chaglovne.l2.database.DatabaseManager
import java.io.File

class DefaultRootComponent(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val db = File("database.db")
    private val databaseManager = DatabaseManager(db.absolutePath)
    private var macroJob: Job? = null
    private val navigation = StackNavigation<Config>()
    private val _model = MutableValue(RootComponent.Model(isMacroSelected = true, isEditorSelected = false, isProfileSelected = false))
    private val _stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Macros,
        childFactory = ::child
    )
    override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack
    override val model: Value<RootComponent.Model> = _model

    private fun onMacrosTabClicked() {
        _model.update { it.copy(isMacroSelected = true, isProfileSelected = false, isEditorSelected = false) }
        navigation.bringToFront(Config.Macros)
    }

    private fun onEditorTabClicked() {
        _model.update { it.copy(isMacroSelected = false, isProfileSelected = false, isEditorSelected = true) }
        navigation.bringToFront(Config.Editor)
    }

    private fun onProfileTabClicked() {
        _model.update { it.copy(isMacroSelected = false, isProfileSelected = true, isEditorSelected = false) }
        navigation.bringToFront(Config.Profile)
    }

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onMacroStartStop(isLaunched: Boolean) {
        if (isLaunched) {
            macroJob = macroStart(macros = databaseManager.getMacros(), scope = GlobalScope)
        } else {
            macroJob?.cancel()
            macroJob = null
        }
    }

    override fun outputHandler(output: RootComponent.Output) {
        when (output) {
            RootComponent.Output.EditorClick -> { onEditorTabClicked() }
            RootComponent.Output.MacroClick -> { onMacrosTabClicked() }
            RootComponent.Output.ProfileClick -> { onProfileTabClicked() }
            is RootComponent.Output.MacroStartStop -> { onMacroStartStop(output.isLaunched) }
        }
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.Editor -> RootComponent.Child.EditorChild(DefaultEditorComponent(componentContext, databaseManager))
            Config.Macros -> RootComponent.Child.MacroChild(DefaultMacrosComponent(componentContext, databaseManager) { onEditorTabClicked() })
            Config.Profile -> RootComponent.Child.ProfileChild(DefaultProfileComponent(componentContext))
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Macros: Config
        @Serializable
        data object Editor: Config
        @Serializable
        data object Profile: Config
    }
}