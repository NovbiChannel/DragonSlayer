package ru.chaglovne.l2.components.root.ui_logic

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.chaglovne.l2.components.editor.ui_logic.DefaultEditorComponent
import ru.chaglovne.l2.components.macros.ui_logic.DefaultMacrosComponent
import ru.chaglovne.l2.components.settings.ui_logic.DefaultSettingsComponent

class DefaultRootComponent(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val _stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Macros,
        childFactory = ::child
    )
    override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack

    override fun onMacrosTabClicked() {
        navigation.bringToFront(Config.Macros)
    }

    override fun onEditorTabClicked() {
        navigation.bringToFront(Config.Editor)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.Editor -> RootComponent.Child.EditorChild(DefaultEditorComponent(componentContext))
            Config.Macros -> RootComponent.Child.MacroChild(DefaultMacrosComponent(componentContext))
            Config.Settings -> RootComponent.Child.SettingsChild(DefaultSettingsComponent(componentContext))
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Macros: Config
        @Serializable
        data object Editor: Config
        @Serializable
        data object Settings: Config
    }
}