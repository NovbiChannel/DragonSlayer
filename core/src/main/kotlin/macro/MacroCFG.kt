package macro

import KeyPressConfig
import min
import java.awt.event.KeyEvent

val f1tof12 = listOf(
    KeyPressConfig(KeyEvent.VK_F1),
    KeyPressConfig(KeyEvent.VK_F2),
    KeyPressConfig(KeyEvent.VK_F3),
    KeyPressConfig(KeyEvent.VK_F4),
    KeyPressConfig(KeyEvent.VK_F5),
    KeyPressConfig(KeyEvent.VK_F6),
    KeyPressConfig(KeyEvent.VK_F7),
    KeyPressConfig(KeyEvent.VK_F8),
    KeyPressConfig(KeyEvent.VK_F9),
    KeyPressConfig(KeyEvent.VK_F10),
    KeyPressConfig(KeyEvent.VK_F11),
    KeyPressConfig(KeyEvent.VK_F12, 10.min)
)