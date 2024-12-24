package macro

import EventConfig
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import sec
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

data class Macro(
    val description: String,
    val comment: String,
    val startStopKey: Int,
    val loopType: LoopType,
    val keys: List<EventConfig>
)

sealed class LoopType {
    data object ONCE: LoopType()
    data object INFINITE: LoopType()
    data class CUSTOM(val repetitions: Int): LoopType()
}

val sumF1toF3 = Macro(
    description = "Нажатие от f1 до f3 с минимальной задержкой",
    comment = "Yours solo target farm",
    startStopKey = NativeKeyEvent.VC_BACKQUOTE,
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F3),
        EventConfig(KeyEvent.VK_F4),
    )
)

val leftMouseClick = Macro(
    description = "Нажатие левой кнопки мыши с минимальной задержкой",
    comment = "none",
    startStopKey = NativeKeyEvent.VC_BACKQUOTE,
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(MouseEvent.BUTTON1, interval = 10.sec),
    )
)