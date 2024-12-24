import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import notification.sendNotification
import java.awt.event.KeyEvent
import java.util.concurrent.atomic.AtomicBoolean

class KeyListener(private val startStopKeyEvent: Int): NativeKeyListener {
    private val isRunning = AtomicBoolean(false)

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        val startStopKeyText = NativeKeyEvent.getKeyText(startStopKeyEvent)
        if (e.keyCode == startStopKeyEvent) {
            isRunning.set(!isRunning.get())
            val infoMessage = if (isRunning.get()) "Макрос запущен, для остановки нажми $startStopKeyText" else "Макрос остановлен"
            sendNotification(infoMessage)
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {}
    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    fun isRunning(): Boolean {
        return isRunning.get()
    }
}