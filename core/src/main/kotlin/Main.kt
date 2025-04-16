import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import keyboard.WindowsKeyCode
import kotlinx.coroutines.*
import listeners.KeyboardListener
import listeners.MouseListener
import mouse.MouseKeyCode
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
                    is InputType.KEYBOARD -> WindowsKeyCode.getKeyName(inputType.value)
                    is InputType.MOUSE -> MouseKeyCode.getKeyName(inputType.value)
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
        handlePress(event.key, event.timeUnit) { keyDown(event.key) }
    }
    fun handleKeyRelease(event: EventType.KeyRelease) {
        keyUp(event.key)
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