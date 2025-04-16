package sender

import natives.InterceptionKeyState
import natives.InterceptionKeyStroke
import natives.InterceptionLibrary

class InterceptionInputSender : InputSender {
    override fun keyDown(keyCode: Int) {
        sendKey(1, keyCode.toShort(), true)
    }

    override fun keyUp(keyCode: Int) {
        sendKey(1, keyCode.toShort(), false)
    }

    override fun mouseDown(keyCode: Int) {
        TODO("Not yet implemented")
    }

    override fun mouseUp(keyCode: Int) {
        TODO("Not yet implemented")
    }

    private fun sendKey(device: Int, code: Short, isDown: Boolean) {
        val context = InterceptionLibrary.INSTANCE.interception_create_context()
        val stroke = InterceptionKeyStroke().apply {
            this.code = code
            this.state = if (isDown) InterceptionKeyState.KEY_DOWN.toShort()
            else InterceptionKeyState.KEY_UP.toShort()
        }
        stroke.write()
        InterceptionLibrary.INSTANCE.interception_send(context, device, stroke.pointer, 1)
        InterceptionLibrary.INSTANCE.interception_destroy_context(context)
    }
}