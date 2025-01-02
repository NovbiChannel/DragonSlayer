import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import macro.Macro
import notification.sendNotification
import java.util.concurrent.atomic.AtomicBoolean

class KeyListener(
    private val macro: Macro,
    private val runningMacros: MutableMap<Macro, Boolean>
) : NativeKeyListener {
    private val isRunning = AtomicBoolean(false)

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if (e.keyCode == macro.startStopKey) {
            if (isRunning.get()) {
                isRunning.set(false)
                runningMacros[macro] = false
                sendNotification("Макрос '${macro.title}' остановлен")
            } else {
                runningMacros.keys.forEach { otherMacro ->
                    if (otherMacro != macro) {
                        runningMacros[otherMacro] = false
                    }
                }
                isRunning.set(true)
                runningMacros[macro] = true
                sendNotification("Макрос '${macro.title}' запущен, для остановки нажми ${NativeKeyEvent.getKeyText(macro.startStopKey)}")
            }
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {}
    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    fun isRunning(): Boolean {
        return isRunning.get()
    }
}