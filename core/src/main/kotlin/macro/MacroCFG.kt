package macro

import EventConfig
import InputType
import LoopType
import Macro
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import min
import sec
import java.awt.event.KeyEvent

val seEssenceMPRegen = Macro(
    title = "ШЕ Essence МП реген 90c",
    description = "МП реген раз в 90c",
    comment = "f1 - тычка, f2 - подобрать, f3 - МП реген",
    inputType = InputType.MOUSE(NativeMouseEvent.BUTTON4),
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3, interval = 1.min),
        //EventConfig(KeyEvent.VK_F4, interval = 5.min)
    )
)

val sumF1toF3 = Macro(
    title = "Соло таргет без баланса",
    description = "Нажатие от f1 до f3 с минимальной задержкой",
    comment = "Yours соло таргет фарм без баланса",
    inputType = InputType.MOUSE(NativeMouseEvent.BUTTON4),
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3)
    )
)

val sumF1toF4 = Macro(
    title = "Соло таргет + баланс",
    description = "Нажатие от f1 до f3 с минимальной задержкой, f4 - интервал нажатия 1 минута",
    comment = "Yours соло таргет, фарм Алигаторов",
    inputType = InputType.MOUSE(NativeMouseEvent.BUTTON5),
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3),
        EventConfig(KeyEvent.VK_F4),
    )
)
val sumF1toF6AOE = Macro(
    title = "АОЕ + баланс",
    description = "Нажатие от f1 до f5 с минимальной задержкой, f6 - интервал нажатия 3 минуты",
    comment = "Yours AOE farm",
    inputType = InputType.KEYBOARD(NativeKeyEvent.VC_BACKQUOTE),
    loopType = LoopType.INFINITE,
    keys = listOf(
        EventConfig(KeyEvent.VK_F1),
        EventConfig(KeyEvent.VK_F2),
        EventConfig(KeyEvent.VK_F3),
        EventConfig(KeyEvent.VK_F4),
        EventConfig(KeyEvent.VK_F5),
        EventConfig(KeyEvent.VK_F6, interval = 1.min)
    )
)