package listeners

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseListener
import macro.InputType
import macro.Macro
import notification.sendNotification

class KeyListener(
    private val macro: Macro,
    private val runningMacros: MutableMap<Macro, Boolean>
) : NativeKeyListener {

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        when (val inputType = macro.inputType) {
            is InputType.KEYBOARD -> {
                if (e.keyCode == inputType.value) {
                    toggleMacroState(macro, runningMacros)
                }
            }
            is InputType.MOUSE -> {}
        }

    }
}

class MouseListener(
    private val macro: Macro,
    private val runningMacros: MutableMap<Macro, Boolean>
): NativeMouseListener {

    override fun nativeMousePressed(nativeEvent: NativeMouseEvent?) {
        when (val inputType = macro.inputType) {
            is InputType.MOUSE -> {
                if (nativeEvent?.button == inputType.value) {
                    toggleMacroState(macro, runningMacros)
                }
            }
            is InputType.KEYBOARD -> {}
        }
    }
}


private fun toggleMacroState(
    macro: Macro,
    runningMacros: MutableMap<Macro, Boolean>
) {
    val message: String
    val key = when (val inputType = macro.inputType) {
        is InputType.KEYBOARD -> inputType.value
        is InputType.MOUSE -> inputType.value
    }
    if (runningMacros[macro] == true) {
        runningMacros[macro] = false
        message = "Макрос '${macro.title}' остановлен"
    } else {
        runningMacros[macro] = true
        message = "Макрос '${macro.title}' запущен, для остановки нажми ${NativeKeyEvent.getKeyText(key)}"
    }
    sendNotification(message)
}