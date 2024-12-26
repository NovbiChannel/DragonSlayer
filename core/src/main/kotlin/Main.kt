import com.github.kwhat.jnativehook.GlobalScreen
import kotlinx.coroutines.delay
import macro.*
import java.awt.Robot

suspend fun main() { macroStart(sumF1toF6AOE) }

suspend fun macroStart(macros: Macro) {
    val robot = Robot()
    val keyListener = KeyListener(macros.startStopKey)

    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(keyListener)

    val lastPressTimes = mutableMapOf<Int, Long>()

    try {
        when (val loopType = macros.loopType) {
            is LoopType.SINGLE -> {
                executeMacro(robot, lastPressTimes, macros)
            }
            is LoopType.INFINITE -> {
                while (true) {
                    if (keyListener.isRunning()) {
                        executeMacro(robot, lastPressTimes, macros)
                    } else {
                        delay(100)
                    }
                }
            }
            is LoopType.CUSTOM -> {
                repeat(loopType.repetitions) {
                    if (keyListener.isRunning()) {
                        executeMacro(robot, lastPressTimes, macros)
                    } else {
                        delay(100)
                    }
                }
            }
        }
    } finally {
        GlobalScreen.unregisterNativeHook()
        println("Программа остановлена.")
    }
}

private suspend fun executeMacro(
    robot: Robot,
    lastPressTimes: MutableMap<Int, Long>,
    macros: Macro
) {
    macros.keys.forEach { config ->
        val lastPressTime = lastPressTimes[config.eventKey] ?: 0L
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastPressTime >= config.interval) {
            robot.keyPress(config.eventKey)
            robot.keyRelease(config.eventKey)

            lastPressTimes[config.eventKey] = currentTime
            delay(config.delay)
        }
    }
}