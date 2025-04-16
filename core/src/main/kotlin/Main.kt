import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import kotlinx.coroutines.*
import listeners.KeyboardListener
import listeners.MouseListener
import notification.sendNotification
import sender.DefaultInputSender
import sender.InputSender

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
        val sender = DefaultInputSender()
        val lastPressTimes = mutableMapOf<Int, Long>()
        val runningMacros = mutableMapOf<Macro, Boolean>()

        val listenerJob: Job = onInputListener(scope) { inputType ->
            val findMacro = macros.find { it.inputType == inputType }

            findMacro?.let {
                val inputKeyTitle = when(inputType) {
                    is InputType.KEYBOARD -> NativeKeyEvent.getKeyText(inputType.value)
                    is InputType.MOUSE -> MouseKeyCodes.getKeyName(inputType.value)
                }
                val message: String
                if (runningMacros[findMacro] == true) {
                    runningMacros[findMacro] = false
                    message = "Макрос '${findMacro.title}' остановлен"
                } else {
                    macros.forEach { macro -> if (macro != findMacro) runningMacros[macro] = false }
                    runningMacros[findMacro] = true
                    message = "Макрос '${findMacro.title}' запущен, для остановки нажми $inputKeyTitle"
                }
                if (it.isShowNotification) {
                    sendNotification(message)
                }
            }
        }

        try {
            while (isActive) {
                macros.forEach { macro ->
                    if (runningMacros[macro] == true) {
                        when (val loopType = macro.loopType) {
                            is LoopType.SINGLE -> {
                                sender.execute(lastPressTimes, macro)
                                runningMacros[macro] = false
                            }
                            is LoopType.INFINITE -> {
                                while (runningMacros[macro] == true) {
                                    sender.execute(lastPressTimes, macro)
                                }
                            }
                            is LoopType.CUSTOM -> {
                                repeat(loopType.repetitions) {
                                    sender.execute(lastPressTimes, macro)
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

private suspend fun InputSender.execute(
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
        val currentKey = convertNativeToWindowsKeyCode(event.key)
        handlePress(currentKey, event.timeUnit) { keyDown(currentKey) }
    }
    fun handleKeyRelease(event: EventType.KeyRelease) {
        val currentKey = convertNativeToWindowsKeyCode(event.key)
        keyUp(currentKey)
    }

    fun handleMousePress(event: EventType.MousePress) {
        handlePress(event.key, event.timeUnit) { keyDown(event.key) }
    }
    fun handleMouseRelease(event: EventType.MouseRelease) {
        mouseUp(event.key)
    }

    macro.events.forEach { event ->
        when (event) {
            is EventType.Delay -> delay(event.timeUnit.delay())
            is EventType.KeyPress -> handleKeyPress(event)
            is EventType.KeyRelease -> handleKeyRelease(event)
            is EventType.MousePress -> handleMousePress(event)
            is EventType.MouseRelease -> handleMouseRelease(event)
        }
    }
}

fun convertNativeToWindowsKeyCode(nativeKeyCode: Int): Int {
    return when (nativeKeyCode) {
        NativeKeyEvent.VC_A -> 0x41
        NativeKeyEvent.VC_B -> 0x42
        NativeKeyEvent.VC_C -> 0x43
        NativeKeyEvent.VC_D -> 0x44
        NativeKeyEvent.VC_E -> 0x45
        NativeKeyEvent.VC_F -> 0x46
        NativeKeyEvent.VC_G -> 0x47
        NativeKeyEvent.VC_H -> 0x48
        NativeKeyEvent.VC_I -> 0x49
        NativeKeyEvent.VC_J -> 0x4A
        NativeKeyEvent.VC_K -> 0x4B
        NativeKeyEvent.VC_L -> 0x4C
        NativeKeyEvent.VC_M -> 0x4D
        NativeKeyEvent.VC_N -> 0x4E
        NativeKeyEvent.VC_O -> 0x4F
        NativeKeyEvent.VC_P -> 0x50
        NativeKeyEvent.VC_Q -> 0x51
        NativeKeyEvent.VC_R -> 0x52
        NativeKeyEvent.VC_S -> 0x53
        NativeKeyEvent.VC_T -> 0x54
        NativeKeyEvent.VC_U -> 0x55
        NativeKeyEvent.VC_V -> 0x56
        NativeKeyEvent.VC_W -> 0x57
        NativeKeyEvent.VC_X -> 0x58
        NativeKeyEvent.VC_Y -> 0x59
        NativeKeyEvent.VC_Z -> 0x5A

        NativeKeyEvent.VC_0 -> 0x30
        NativeKeyEvent.VC_1 -> 0x31
        NativeKeyEvent.VC_2 -> 0x32
        NativeKeyEvent.VC_3 -> 0x33
        NativeKeyEvent.VC_4 -> 0x34
        NativeKeyEvent.VC_5 -> 0x35
        NativeKeyEvent.VC_6 -> 0x36
        NativeKeyEvent.VC_7 -> 0x37
        NativeKeyEvent.VC_8 -> 0x38
        NativeKeyEvent.VC_9 -> 0x39

        NativeKeyEvent.VC_ENTER -> 0x0D
        NativeKeyEvent.VC_ESCAPE -> 0x1B
        NativeKeyEvent.VC_BACKSPACE -> 0x08
        NativeKeyEvent.VC_TAB -> 0x09
        NativeKeyEvent.VC_SPACE -> 0x20

        NativeKeyEvent.VC_MINUS -> 0xBD
        NativeKeyEvent.VC_EQUALS -> 0xBB
        NativeKeyEvent.VC_OPEN_BRACKET -> 0xDB
        NativeKeyEvent.VC_CLOSE_BRACKET -> 0xDD
        NativeKeyEvent.VC_BACK_SLASH -> 0xDC
        NativeKeyEvent.VC_SEMICOLON -> 0xBA
        NativeKeyEvent.VC_QUOTE -> 0xDE
        NativeKeyEvent.VC_COMMA -> 0xBC
        NativeKeyEvent.VC_PERIOD -> 0xBE
        NativeKeyEvent.VC_SLASH -> 0xBF
        NativeKeyEvent.VC_BACKQUOTE -> 0xC0

        NativeKeyEvent.VC_SHIFT -> 0x10
        NativeKeyEvent.VC_CONTROL -> 0x11
        NativeKeyEvent.VC_ALT -> 0x12
        NativeKeyEvent.VC_CAPS_LOCK -> 0x14

        NativeKeyEvent.VC_UP -> 0x26
        NativeKeyEvent.VC_DOWN -> 0x28
        NativeKeyEvent.VC_LEFT -> 0x25
        NativeKeyEvent.VC_RIGHT -> 0x27

        NativeKeyEvent.VC_INSERT -> 0x2D
        NativeKeyEvent.VC_DELETE -> 0x2E
        NativeKeyEvent.VC_HOME -> 0x24
        NativeKeyEvent.VC_END -> 0x23
        NativeKeyEvent.VC_PAGE_UP -> 0x21
        NativeKeyEvent.VC_PAGE_DOWN -> 0x22

        NativeKeyEvent.VC_NUM_LOCK -> 0x90
        NativeKeyEvent.VC_SCROLL_LOCK -> 0x91

        NativeKeyEvent.VC_F1 -> 0x70
        NativeKeyEvent.VC_F2 -> 0x71
        NativeKeyEvent.VC_F3 -> 0x72
        NativeKeyEvent.VC_F4 -> 0x73
        NativeKeyEvent.VC_F5 -> 0x74
        NativeKeyEvent.VC_F6 -> 0x75
        NativeKeyEvent.VC_F7 -> 0x76
        NativeKeyEvent.VC_F8 -> 0x77
        NativeKeyEvent.VC_F9 -> 0x78
        NativeKeyEvent.VC_F10 -> 0x79
        NativeKeyEvent.VC_F11 -> 0x7A
        NativeKeyEvent.VC_F12 -> 0x7B

        else -> throw IllegalArgumentException("Unsupported NativeKeyEvent code: $nativeKeyCode")
    }
}