package listeners

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseListener
import macro.Macro
import notification.sendNotification

class KeyListener(
    private val macro: Macro,
    private val runningMacros: MutableMap<Macro, Boolean>
) : NativeKeyListener {

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if (e.keyCode == macro.startStopKey) {
            toggleMacroState(macro, runningMacros)
        }
    }
}

class MouseListener(
    private val macro: Macro,
    private val runningMacros: MutableMap<Macro, Boolean>
): NativeMouseListener {

    override fun nativeMousePressed(nativeEvent: NativeMouseEvent?) {
        if (nativeEvent?.button == macro.startStopKey) {
            toggleMacroState(macro, runningMacros)
        }
    }
}

private fun toggleMacroState(
    macro: Macro,
    runningMacros: MutableMap<Macro, Boolean>
) {
    val message: String
    if (runningMacros[macro] == true) {
        runningMacros[macro] = false
        message = "Макрос '${macro.title}' остановлен"
    } else {
        runningMacros[macro] = true
        message = "Макрос '${macro.title}' запущен, для остановки нажми ${NativeKeyEvent.getKeyText(macro.startStopKey)}"
    }
    sendNotification(message)
}