import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import kotlinx.coroutines.*
import listeners.KeyboardListener
import listeners.MouseListener
import notification.sendNotification
import java.awt.Robot
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.security.Key

fun onInputListener(scope: CoroutineScope, output: (InputType) -> Unit): Job {
    val mouseListener = MouseListener(output)
    val keyboardListener = KeyboardListener(output)
    return scope.launch {
        try {
            if (!GlobalScreen.isNativeHookRegistered()) GlobalScreen.registerNativeHook()

            GlobalScreen.addNativeKeyListener(keyboardListener)
            GlobalScreen.addNativeMouseListener(mouseListener)

            while (isActive) { delay(DEFAULT_DELAY) }
        } finally {
            GlobalScreen.removeNativeKeyListener(keyboardListener)
            GlobalScreen.removeNativeMouseListener(mouseListener)
        }
    }
}

fun macroStart(macros: List<Macro>, scope: CoroutineScope): Job {
    return scope.launch(Dispatchers.IO) {
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

        try {
            while (isActive) {
                macros.forEach { macro ->
                    if (runningMacros[macro] == true) {
                        when (val loopType = macro.loopType) {
                            is LoopType.SINGLE -> {
                                execute(robot, lastPressTimes, macro)
                                runningMacros[macro] = false
                            }
                            is LoopType.INFINITE -> {
                                while (runningMacros[macro] == true) {
                                    execute(robot, lastPressTimes, macro)
                                }
                            }
                            is LoopType.CUSTOM -> {
                                repeat(loopType.repetitions) {
                                    execute(robot, lastPressTimes, macro)
                                }
                                runningMacros[macro] = false
                            }
                        }
                    }
                }
            }
        } finally { listenerJob.cancel() }
    }
}

private suspend fun execute(
    robot: Robot,
    lastPressTimes: MutableMap<Int, Long>,
    macro: Macro
) {
    fun handlePress(key: Int, timeUnit: TimeUnit?, action: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        timeUnit?.let {
            val lastPressTime = lastPressTimes[key] ?: 0L
            if (currentTime - lastPressTime >= it.delay()) {
                action()
                lastPressTimes[key] = currentTime
            }
        } ?: run { action() }
    }

    fun handleKeyPress(event: EventType.KeyPress) {
        val currentKey = convertNativeToAWTKeyCode(nativeKeyCode = event.key)
        handlePress(currentKey, event.timeUnit) { robot.keyPress(currentKey) }
    }

    fun handleMousePress(event: EventType.MousePress) {
        handlePress(event.key, event.timeUnit) { robot.mousePress(event.key) }
    }

    macro.events.forEach { event ->
        when (event) {
            is EventType.Delay -> delay(event.timeUnit.delay())
            is EventType.KeyPress -> handleKeyPress(event)
            is EventType.KeyRelease -> robot.keyRelease(convertNativeToAWTKeyCode(nativeKeyCode = event.key))
            is EventType.MousePress -> handleMousePress(event)
            is EventType.MouseRelease -> robot.mouseRelease(event.key)
        }
    }
}

private fun convertNativeToAWTKeyCode(nativeKeyCode: Int): Int {
    return when (nativeKeyCode) {
        NativeKeyEvent.VC_ESCAPE -> KeyEvent.VK_ESCAPE
        NativeKeyEvent.VC_F1 -> KeyEvent.VK_F1
        NativeKeyEvent.VC_F2 -> KeyEvent.VK_F2
        NativeKeyEvent.VC_F3 -> KeyEvent.VK_F3
        NativeKeyEvent.VC_F4 -> KeyEvent.VK_F4
        NativeKeyEvent.VC_F5 -> KeyEvent.VK_F5
        NativeKeyEvent.VC_F6 -> KeyEvent.VK_F6
        NativeKeyEvent.VC_F7 -> KeyEvent.VK_F7
        NativeKeyEvent.VC_F8 -> KeyEvent.VK_F8
        NativeKeyEvent.VC_F9 -> KeyEvent.VK_F9
        NativeKeyEvent.VC_F10 -> KeyEvent.VK_F10
        NativeKeyEvent.VC_F11 -> KeyEvent.VK_F11
        NativeKeyEvent.VC_F12 -> KeyEvent.VK_F12
        NativeKeyEvent.VC_DELETE -> KeyEvent.VK_DELETE
        NativeKeyEvent.VC_BACKQUOTE -> KeyEvent.VK_BACK_QUOTE
        NativeKeyEvent.VC_1 -> KeyEvent.VK_1
        NativeKeyEvent.VC_2 -> KeyEvent.VK_2
        NativeKeyEvent.VC_3 -> KeyEvent.VK_3
        NativeKeyEvent.VC_4 -> KeyEvent.VK_4
        NativeKeyEvent.VC_5 -> KeyEvent.VK_5
        NativeKeyEvent.VC_6 -> KeyEvent.VK_6
        NativeKeyEvent.VC_7 -> KeyEvent.VK_7
        NativeKeyEvent.VC_8 -> KeyEvent.VK_8
        NativeKeyEvent.VC_9 -> KeyEvent.VK_9
        NativeKeyEvent.VC_0 -> KeyEvent.VK_0
        NativeKeyEvent.VC_MINUS -> KeyEvent.VK_MINUS
        NativeKeyEvent.VC_EQUALS -> KeyEvent.VK_EQUALS
        NativeKeyEvent.VC_BACKSPACE -> KeyEvent.VK_BACK_SPACE
        NativeKeyEvent.VC_TAB -> KeyEvent.VK_TAB
        NativeKeyEvent.VC_Q -> KeyEvent.VK_Q
        NativeKeyEvent.VC_W -> KeyEvent.VK_W
        NativeKeyEvent.VC_E -> KeyEvent.VK_E
        NativeKeyEvent.VC_R -> KeyEvent.VK_R
        NativeKeyEvent.VC_T -> KeyEvent.VK_T
        NativeKeyEvent.VC_Y -> KeyEvent.VK_Y
        NativeKeyEvent.VC_U -> KeyEvent.VK_U
        NativeKeyEvent.VC_I -> KeyEvent.VK_I
        NativeKeyEvent.VC_O -> KeyEvent.VK_O
        NativeKeyEvent.VC_P -> KeyEvent.VK_P
        NativeKeyEvent.VC_OPEN_BRACKET -> KeyEvent.VK_OPEN_BRACKET
        NativeKeyEvent.VC_CLOSE_BRACKET -> KeyEvent.VK_CLOSE_BRACKET
        NativeKeyEvent.VC_BACK_SLASH -> KeyEvent.VK_BACK_SLASH
        NativeKeyEvent.VC_CAPS_LOCK -> KeyEvent.VK_CAPS_LOCK
        NativeKeyEvent.VC_A -> KeyEvent.VK_A
        NativeKeyEvent.VC_S -> KeyEvent.VK_S
        NativeKeyEvent.VC_D -> KeyEvent.VK_D
        NativeKeyEvent.VC_F -> KeyEvent.VK_F
        NativeKeyEvent.VC_G -> KeyEvent.VK_G
        NativeKeyEvent.VC_H -> KeyEvent.VK_H
        NativeKeyEvent.VC_J -> KeyEvent.VK_J
        NativeKeyEvent.VC_K -> KeyEvent.VK_K
        NativeKeyEvent.VC_L -> KeyEvent.VK_L
        NativeKeyEvent.VC_SEMICOLON -> KeyEvent.VK_SEMICOLON
        NativeKeyEvent.VC_QUOTE -> KeyEvent.VK_QUOTE
        NativeKeyEvent.VC_ENTER -> KeyEvent.VK_ENTER
        NativeKeyEvent.VC_SHIFT -> KeyEvent.VK_SHIFT
        NativeKeyEvent.VC_Z -> KeyEvent.VK_Z
        NativeKeyEvent.VC_X -> KeyEvent.VK_X
        NativeKeyEvent.VC_C -> KeyEvent.VK_C
        NativeKeyEvent.VC_V -> KeyEvent.VK_V
        NativeKeyEvent.VC_B -> KeyEvent.VK_B
        NativeKeyEvent.VC_N -> KeyEvent.VK_N
        NativeKeyEvent.VC_M -> KeyEvent.VK_M
        NativeKeyEvent.VC_COMMA -> KeyEvent.VK_COMMA
        NativeKeyEvent.VC_PERIOD -> KeyEvent.VK_PERIOD
        NativeKeyEvent.VC_SLASH -> KeyEvent.VK_SLASH
        NativeKeyEvent.VC_CONTROL -> KeyEvent.VK_CONTROL
        NativeKeyEvent.VC_HOME -> KeyEvent.VK_HOME
        NativeKeyEvent.VC_ALT -> KeyEvent.VK_ALT
        NativeKeyEvent.VC_SPACE -> KeyEvent.VK_SPACE
        NativeKeyEvent.VC_LEFT -> KeyEvent.VK_LEFT
        NativeKeyEvent.VC_RIGHT -> KeyEvent.VK_RIGHT
        NativeKeyEvent.VC_UP -> KeyEvent.VK_UP
        NativeKeyEvent.VC_DOWN -> KeyEvent.VK_DOWN
        else -> -1
    }
}