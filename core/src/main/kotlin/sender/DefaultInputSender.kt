package sender

import com.sun.jna.platform.win32.BaseTSD
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.platform.win32.WinUser.INPUT
import com.sun.jna.platform.win32.WinUser.INPUT.INPUT_KEYBOARD
import com.sun.jna.platform.win32.WinUser.INPUT.INPUT_MOUSE
import com.sun.jna.platform.win32.WinUser.KEYBDINPUT

class DefaultInputSender: InputSender {
    companion object {
        private val user32 = User32.INSTANCE

        const val KEYEVENTF_KEYDOWN = 0
        const val KEYEVENTF_KEYUP = 2

        const val MOUSEEVENTF_LEFTDOWN = 0x0002
        const val MOUSEEVENTF_LEFTUP = 0x0004
        const val MOUSEEVENTF_RIGHTDOWN = 0x0008
        const val MOUSEEVENTF_RIGHTUP = 0x0010
        const val MOUSEEVENTF_MIDDLEDOWN = 0x0020
        const val MOUSEEVENTF_MIDDLEUP = 0x0040

    }

    override fun keyDown(keyCode: Int) {
        sendKeyboardInput(keyCode, KEYEVENTF_KEYDOWN)
    }

    override fun keyUp(keyCode: Int) {
        sendKeyboardInput(keyCode, KEYEVENTF_KEYUP)
    }

    override fun mouseDown(keyCode: Int) {
        val flag = when (keyCode) {
            1 -> MOUSEEVENTF_LEFTDOWN
            2 -> MOUSEEVENTF_RIGHTDOWN
            else -> throw IllegalArgumentException("Unsupported mouse code: $keyCode")
        }
        sendMouseInput(flag)
    }

    override fun mouseUp(keyCode: Int) {
        val flag = when (keyCode) {
            1 -> MOUSEEVENTF_LEFTUP
            2 -> MOUSEEVENTF_RIGHTUP
            else -> throw IllegalArgumentException("Unsupported mouse code: $keyCode")
        }
        sendMouseInput(flag)
    }

    private fun sendKeyboardInput(keyCode: Int, flag: Int) {
        val input = INPUT().apply {
            type = WinDef.DWORD(INPUT_KEYBOARD.toLong())
            input.setType("ki")
            input.ki = KEYBDINPUT().apply {
                wVk = WinDef.WORD(keyCode.toLong())
                wScan = WinDef.WORD(0)
                time = WinDef.DWORD(0)
                dwExtraInfo = BaseTSD.ULONG_PTR(0)
                dwFlags = WinDef.DWORD(flag.toLong())
            }
        }
        user32.SendInput(WinDef.DWORD(1), arrayOf(input), input.size())
    }

    private fun sendMouseInput(flag: Int) {
        val input = INPUT().apply {
            type = WinDef.DWORD(INPUT_MOUSE.toLong())
            input.setType("mi")
            input.mi = WinUser.MOUSEINPUT().apply {
                dx = WinDef.LONG(0)
                dy = WinDef.LONG(0)
                mouseData = WinDef.DWORD(0)
                dwFlags = WinDef.DWORD(flag.toLong())
                time = WinDef.DWORD(0)
                dwExtraInfo = BaseTSD.ULONG_PTR(0)
            }
        }
        user32.SendInput(WinDef.DWORD(1), arrayOf(input), input.size())
    }
}