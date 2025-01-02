import com.github.kwhat.jnativehook.GlobalScreen
import kotlinx.coroutines.delay
import listeners.KeyListener
import listeners.MouseListener
import macro.*
import notification.sendNotification
import java.awt.Robot

suspend fun main() {
    val myMacros = listOf(sumF1toF3, sumF1toF4, sumF1toF6AOE)
    macroStart(myMacros)
}

suspend fun macroStart(macros: List<Macro>) {
    val robot = Robot()
    val lastPressTimes = mutableMapOf<Int, Long>()
    val runningMacros = mutableMapOf<Macro, Boolean>()

    val keyListeners = macros.map { KeyListener(it, runningMacros) }
    val mouseListeners = macros.map { MouseListener(it, runningMacros) }

    GlobalScreen.registerNativeHook()
    keyListeners.forEach { GlobalScreen.addNativeKeyListener(it) }
    mouseListeners.forEach { GlobalScreen.addNativeMouseListener(it) }

    try {
        while (true) {
            macros.forEach { macro ->
                if (runningMacros[macro] == true) {
                    when (val loopType = macro.loopType) {
                        is LoopType.SINGLE -> {
                            executeMacro(robot, lastPressTimes, macro)
                        }
                        is LoopType.INFINITE -> {
                            while (runningMacros[macro] == true) {
                                executeMacro(robot, lastPressTimes, macro)
                            }
                        }
                        is LoopType.CUSTOM -> {
                            repeat(loopType.repetitions) {
                                executeMacro(robot, lastPressTimes, macro)
                            }
                        }
                    }
                }
            }
        }
    } finally {
        GlobalScreen.unregisterNativeHook()
        sendNotification("Программа остановлена")
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