package listeners

import InputType
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseListener

class KeyboardListener(private val output: (InputType) -> Unit): NativeKeyListener {
    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        nativeEvent?.keyCode?.let { keyCode ->
            output(InputType.KEYBOARD(keyCode))
        }
    }
}

class MouseListener(private val output: (InputType) -> Unit): NativeMouseListener {
    override fun nativeMousePressed(nativeEvent: NativeMouseEvent?) {
        nativeEvent?.button?.let { keyCode ->
            output(InputType.MOUSE(keyCode))
        }
    }
}