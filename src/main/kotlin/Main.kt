import com.github.kwhat.jnativehook.GlobalScreen
import java.awt.Robot
import java.awt.event.KeyEvent
import kotlin.concurrent.thread

fun main() {
    val robot = Robot()
    val fKeys = listOf(
        KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3,
        KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6,
        KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9,
        KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12
    )

    val keyListener = KeyListener()
    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(keyListener)

    val keyPressThread = thread {
        while (true) {
            if (keyListener.isRunning()) {
                for (key in fKeys) {
                    robot.keyPress(key)
                    robot.keyRelease(key)
                    Thread.sleep(100)
                }
            } else {
                Thread.sleep(100)
            }
        }
    }

    println("Нажмите Enter для остановки программы...")
    readlnOrNull()
    keyPressThread.interrupt()
    GlobalScreen.unregisterNativeHook()
    println("Программа остановлена.")
}