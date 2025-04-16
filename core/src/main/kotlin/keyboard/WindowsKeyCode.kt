package keyboard

object WindowsKeyCode {
    const val VC_A = 0x41
    const val VC_B = 0x42
    const val VC_C = 0x43
    const val VC_D = 0x44
    const val VC_E = 0x45
    const val VC_F = 0x46
    const val VC_G = 0x47
    const val VC_H = 0x48
    const val VC_I = 0x49
    const val VC_J = 0x4A
    const val VC_K = 0x4B
    const val VC_L = 0x4C
    const val VC_M = 0x4D
    const val VC_N = 0x4E
    const val VC_O = 0x4F
    const val VC_P = 0x50
    const val VC_Q = 0x51
    const val VC_R = 0x52
    const val VC_S = 0x53
    const val VC_T = 0x54
    const val VC_U = 0x55
    const val VC_V = 0x56
    const val VC_W = 0x57
    const val VC_X = 0x58
    const val VC_Y = 0x59
    const val VC_Z = 0x5A
    const val VC_0 = 0x30
    const val VC_1 = 0x31
    const val VC_2 = 0x32
    const val VC_3 = 0x33
    const val VC_4 = 0x34
    const val VC_5 = 0x35
    const val VC_6 = 0x36
    const val VC_7 = 0x37
    const val VC_8 = 0x38
    const val VC_9 = 0x39
    const val VC_ENTER = 0x0D
    const val VC_ESCAPE = 0x1B
    const val VC_BACKSPACE = 0x08
    const val VC_TAB = 0x09
    const val VC_SPACE = 0x20
    const val VC_MINUS = 0xBD
    const val VC_EQUALS = 0xBB
    const val VC_OPEN_BRACKET = 0xDB
    const val VC_CLOSE_BRACKET = 0xDD
    const val VC_BACK_SLASH = 0xDC
    const val VC_SEMICOLON = 0xBA
    const val VC_QUOTE = 0xDE
    const val VC_COMMA = 0xBC
    const val VC_PERIOD = 0xBE
    const val VC_SLASH = 0xBF
    const val VC_BACKQUOTE = 0xC0
    const val VC_SHIFT = 0x10
    const val VC_CONTROL = 0x11
    const val VC_ALT = 0x12
    const val VC_CAPS_LOCK = 0x14
    const val VC_UP = 0x26
    const val VC_DOWN = 0x28
    const val VC_LEFT = 0x25
    const val VC_RIGHT = 0x27
    const val VC_INSERT = 0x2D
    const val VC_DELETE = 0x2E
    const val VC_HOME = 0x24
    const val VC_END = 0x23
    const val VC_PAGE_UP= 0x21
    const val VC_PAGE_DOWN = 0x22
    const val VC_NUM_LOCK = 0x90
    const val VC_SCROLL_LOCK = 0x91
    const val VC_F1 = 0x70
    const val VC_F2 = 0x71
    const val VC_F3 = 0x72
    const val VC_F4 = 0x73
    const val VC_F5 = 0x74
    const val VC_F6 = 0x75
    const val VC_F7 = 0x76
    const val VC_F8 = 0x77
    const val VC_F9 = 0x78
    const val VC_F10 = 0x79
    const val VC_F11 = 0x7A
    const val VC_F12 = 0x7B

    fun getKeyName(key: Int): String {
        return when (key) {
            VC_A -> "A"
            VC_B -> "B"
            VC_C -> "C"
            VC_D -> "D"
            VC_E -> "E"
            VC_F -> "F"
            VC_G -> "G"
            VC_H -> "H"
            VC_I -> "I"
            VC_J -> "J"
            VC_K -> "K"
            VC_L -> "L"
            VC_M -> "M"
            VC_N -> "N"
            VC_O -> "O"
            VC_P -> "P"
            VC_Q -> "Q"
            VC_R -> "R"
            VC_S -> "S"
            VC_T -> "T"
            VC_U -> "U"
            VC_V -> "V"
            VC_W -> "W"
            VC_X -> "X"
            VC_Y -> "Y"
            VC_Z -> "Z"
            VC_0 -> "0"
            VC_1 -> "1"
            VC_2 -> "2"
            VC_3 -> "3"
            VC_4 -> "4"
            VC_5 -> "5"
            VC_6 -> "6"
            VC_7 -> "7"
            VC_8 -> "8"
            VC_9 -> "9"
            VC_ENTER -> "Enter"
            VC_ESCAPE -> "Escape"
            VC_BACKSPACE -> "Backspace"
            VC_TAB -> "Tab"
            VC_SPACE -> "Space"
            VC_MINUS -> "-"
            VC_EQUALS -> "="
            VC_OPEN_BRACKET -> "["
            VC_CLOSE_BRACKET -> "]"
            VC_BACK_SLASH -> "\\"
            VC_SEMICOLON -> ";"
            VC_QUOTE -> "'"
            VC_COMMA -> ","
            VC_PERIOD -> "."
            VC_SLASH -> "/"
            VC_BACKQUOTE -> "`"
            VC_SHIFT -> "Shift"
            VC_CONTROL -> "Control"
            VC_ALT -> "Alt"
            VC_CAPS_LOCK -> "Caps Lock"
            VC_UP -> "Up"
            VC_DOWN -> "Down"
            VC_LEFT -> "Left"
            VC_RIGHT -> "Right"
            VC_INSERT -> "Insert"
            VC_DELETE -> "Delete"
            VC_HOME -> "Home"
            VC_END -> "End"
            VC_PAGE_UP -> "Page Up"
            VC_PAGE_DOWN -> "Page Down"
            VC_NUM_LOCK -> "Num Lock"
            VC_SCROLL_LOCK -> "Scroll Lock"
            VC_F1 -> "F1"
            VC_F2 -> "F2"
            VC_F3 -> "F3"
            VC_F4 -> "F4"
            VC_F5 -> "F5"
            VC_F6 -> "F6"
            VC_F7 -> "F7"
            VC_F8 -> "F8"
            VC_F9 -> "F9"
            VC_F10 -> "F10"
            VC_F11 -> "F11"
            VC_F12 -> "F12"
            else -> throw IllegalArgumentException("Неизвестный код клавиши: $key")
        }
    }
}