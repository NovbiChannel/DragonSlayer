package macro

import EventConfig
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import min
import sec
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

data class Macro(
    val title: String,
    val description: String,
    val comment: String,
    val startStopKey: Int,
    val loopType: LoopType,
    val keys: List<EventConfig>
)

sealed class LoopType {
    data object SINGLE: LoopType()
    data object INFINITE: LoopType()
    data class CUSTOM(val repetitions: Int): LoopType()
}

val sumF1toF3 = Macro(
    title = "Sum solo targer without balance",
    description = "Нажатие от f1 до f3 с минимальной задержкой",
    comment = "Yours соло таргет фарм без баланса",
    startStopKey = NativeMouseEvent.BUTTON4,
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3)
    )
)

val sumF1toF4 = Macro(
    title = "Sum solo target with a balance",
    description = "Нажатие от f1 до f3 с минимальной задержкой, f4 - интервал нажатия 1 минута",
    comment = "Yours соло таргет, фарм Алигаторов",
    startStopKey = NativeMouseEvent.BUTTON5,
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3),
        EventConfig(KeyEvent.VK_F4, interval = 1.min),
    )
)
val sumF1toF6AOE = Macro(
    title = "Sum AOE farm with a balance",
    description = "Нажатие от f1 до f5 с минимальной задержкой, f6 - интервал нажатия 3 минуты",
    comment = "Yours AOE farm",
    startStopKey = NativeKeyEvent.VC_BACKQUOTE,
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3),
        EventConfig(KeyEvent.VK_F4),
        EventConfig(KeyEvent.VK_F5),
        EventConfig(KeyEvent.VK_F6, interval = 3.min)
    )
)