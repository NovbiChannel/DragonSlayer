import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import kotlinx.coroutines.delay
import macro.f1tof12
import notification.sendNotification
import java.awt.Robot
import java.awt.event.KeyEvent

suspend fun main() {
    macroStart(
        macros = f1tof12,
        startStopKeyEvent = NativeKeyEvent.VC_P
    )
}

suspend fun macroStart(macros: List<KeyPressConfig>, startStopKeyEvent: Int) {
    val robot = Robot()
    val keyListener = KeyListener(startStopKeyEvent)

    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(keyListener)

    val lastPressTimes = mutableMapOf<Int, Long>()

    try {
        while (true) {
            if (keyListener.isRunning()) {
                val currentTime = System.currentTimeMillis()

                macros.forEach { config ->
                    val lastPressTime = lastPressTimes[config.key] ?: 0L
                    if (currentTime - lastPressTime >= config.interval) {
                        robot.keyPress(config.key)
                        robot.keyRelease(config.key)
                        lastPressTimes[config.key] = currentTime

                        delay(100)
                    }
                }
            } else {
                delay(100)
            }
        }
    } finally {
        GlobalScreen.unregisterNativeHook()
        println("Программа остановлена.")
    }
}