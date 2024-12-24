package macro

import KeyPressConfig
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import java.awt.event.KeyEvent

data class Macro(
    val description: String,
    val comment: String,
    val startStopKey: Int,
    val keys: List<KeyPressConfig>
)

val sumF1toF3 = Macro(
    description = "Нажатие от f1 до f3 с минимальной задержкой",
    comment = "Yours solo target farm",
    startStopKey = NativeKeyEvent.VC_BACKQUOTE,
    keys = listOf(
        KeyPressConfig(KeyEvent.VK_F1),
        KeyPressConfig(KeyEvent.VK_F2),
        KeyPressConfig(KeyEvent.VK_F3),
    )
)