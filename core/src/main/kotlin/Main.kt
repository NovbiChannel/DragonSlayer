import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import kotlinx.coroutines.*
import listeners.KeyboardListener
import listeners.MouseListener
import notification.sendNotification
import java.awt.Robot

fun onInputListener(scope: CoroutineScope, output: (InputType) -> Unit): Job {
    return scope.launch {
        addListeners(output)

        while (isActive) {
            delay(DEFAULT_DELAY)
        }

        GlobalScreen.unregisterNativeHook()
    }
}

fun macroStart(macros: List<Macro>, scope: CoroutineScope): Job {
    println(macros)
    return scope.launch {
        val robot = Robot()
        val lastPressTimes = mutableMapOf<Int, Long>()
        val runningMacros = mutableMapOf<Macro, Boolean>()

        val listenerJob: Job = onInputListener(scope) { inputType ->
            val findMacro = macros.find { it.inputType == inputType }

            findMacro?.let {
                val message: String
                val key = when (inputType) {
                    is InputType.KEYBOARD -> inputType.value
                    is InputType.MOUSE -> inputType.value
                }
                if (runningMacros[findMacro] == true) {
                    runningMacros[findMacro] = false
                    message = "Макрос '${findMacro.title}' остановлен"
                } else {
                    macros.forEach { macro -> if (macro != findMacro) runningMacros[macro] = false }
                    runningMacros[findMacro] = true
                    message = "Макрос '${findMacro.title}' запущен, для остановки нажми ${NativeKeyEvent.getKeyText(key)}"
                }
                sendNotification(message)
            }
        }

        while (isActive) {
            macros.forEach { macro ->
                if (runningMacros[macro] == true) {
                    when (val loopType = macro.loopType) {
                        is LoopType.SINGLE -> execute(robot, lastPressTimes, macro)
                        is LoopType.INFINITE -> {
                            while (runningMacros[macro] == true) {
                                execute(robot, lastPressTimes, macro)
                            }
                        }
                        is LoopType.CUSTOM -> {
                            repeat(loopType.repetitions) {
                                execute(robot, lastPressTimes, macro)
                            }
                        }
                    }
                }
            }
        }
        listenerJob.cancel()
    }
}

private fun addListeners(output: (InputType) -> Unit) {
    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(KeyboardListener(output))
    GlobalScreen.addNativeMouseListener(MouseListener(output))
}

private suspend fun execute(
    robot: Robot,
    lastPressTimes: MutableMap<Int, Long>,
    macro: Macro
) {
    macro.events.forEach { event ->
        val currentTime = System.currentTimeMillis()

        when (event) {
            is EventType.Delay -> delay(event.timeUnit.delay())
            is EventType.KeyPress -> {
                event.timeUnit?.let {
                    val lastPressTime = lastPressTimes[event.key] ?: 0L
                    if (currentTime - lastPressTime >= it.delay()) robot.keyPress(event.key)
                }?: run { robot.keyPress(event.key) }
            }
            is EventType.KeyRelease -> robot.keyRelease(event.key)
            is EventType.MousePress -> {
                event.timeUnit?.let {
                    val lastPressTime = lastPressTimes[event.key] ?: 0L
                    if (currentTime - lastPressTime >= it.delay()) robot.mousePress(event.key)
                }?: run { robot.mousePress(event.key) }
            }
            is EventType.MouseRelease -> robot.mouseRelease(event.key)
        }
    }
}