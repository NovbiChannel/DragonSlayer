import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import notification.sendNotification
import notification.tray
import notification.trayIcon
import java.awt.SystemTray
import java.util.concurrent.atomic.AtomicBoolean

class KeyListener(private val startStopKeyEvent: Int): NativeKeyListener {
    private val isRunning = AtomicBoolean(false)

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if (e.keyCode == startStopKeyEvent) {
            isRunning.set(!isRunning.get())
            val infoMessage = if (isRunning.get()) "Макрос запущен" else "Макрос остановлен"
            if (isRunning.get().not()) tray.remove(trayIcon)
            sendNotification(infoMessage)
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {}
    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    fun isRunning(): Boolean {
        return isRunning.get()
    }
}