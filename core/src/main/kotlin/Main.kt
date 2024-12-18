import com.github.kwhat.jnativehook.GlobalScreen
import kotlinx.coroutines.delay
import java.awt.Robot
import java.awt.event.KeyEvent

suspend fun main() {
    val robot = Robot()
    val keyListener = KeyListener()

    val keyPressConfigs = listOf(
        KeyPressConfig(KeyEvent.VK_F1),
        KeyPressConfig(KeyEvent.VK_F2),
        KeyPressConfig(KeyEvent.VK_F3),
        KeyPressConfig(KeyEvent.VK_F4),
        KeyPressConfig(KeyEvent.VK_F5),
        KeyPressConfig(KeyEvent.VK_F6),
        KeyPressConfig(KeyEvent.VK_F7),
        KeyPressConfig(KeyEvent.VK_F8),
        KeyPressConfig(KeyEvent.VK_F9),
        KeyPressConfig(KeyEvent.VK_F10),
        KeyPressConfig(KeyEvent.VK_F11),
        KeyPressConfig(KeyEvent.VK_F12, 10.min)
    )

    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(keyListener)

    val lastPressTimes = mutableMapOf<Int, Long>()

    try {
        while (true) {
            if (keyListener.isRunning()) {
                val currentTime = System.currentTimeMillis()

                keyPressConfigs.forEach { config ->
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