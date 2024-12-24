import com.github.kwhat.jnativehook.GlobalScreen
import kotlinx.coroutines.delay
import macro.Macro
import macro.sumF1toF3
import java.awt.Robot

suspend fun main() { macroStart(sumF1toF3) }

suspend fun macroStart(macros: Macro) {
    val robot = Robot()
    val keyListener = KeyListener(macros.startStopKey)

    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(keyListener)

    val lastPressTimes = mutableMapOf<Int, Long>()

    try {
        while (true) {
            if (keyListener.isRunning()) {
                val currentTime = System.currentTimeMillis()

                macros.keys.forEach { config ->
                    val lastPressTime = lastPressTimes[config.key] ?: 0L
                    if (currentTime - lastPressTime >= config.interval) {
                        robot.keyPress(config.key)
                        robot.keyRelease(config.key)
                        lastPressTimes[config.key] = currentTime

                        delay(DEFAULT_DELAY)
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