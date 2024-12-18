import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import java.util.concurrent.atomic.AtomicBoolean

class KeyListener: NativeKeyListener {
    private val isRunning = AtomicBoolean(false)

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if (e.keyCode == NativeKeyEvent.VC_P) {
            isRunning.set(!isRunning.get())
            val infoMessage = if (isRunning.get()) "Макрос запущен" else "Макрос остановлен"
            println(infoMessage)
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {}
    override fun nativeKeyTyped(e: NativeKeyEvent) {}

    fun isRunning(): Boolean {
        return isRunning.get()
    }
}