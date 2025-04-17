package sender

import natives.InterceptionLibrary

class InterceptionInputSender : InputSender {
    val hook = InterceptionLibrary.INSTANCE

    init {
        val result = hook.init_interception()
        if (result == 0) {
            throw IllegalStateException("Interception not initialize")
        }
    }
    override fun keyDown(keyCode: Int) {
        sendKey(keyCode.toShort(), true)
    }

    override fun keyUp(keyCode: Int) {
        sendKey(keyCode.toShort(), false)
    }

    override fun mouseDown(keyCode: Int) {
        TODO("Not yet implemented")
    }

    override fun mouseUp(keyCode: Int) {
        TODO("Not yet implemented")
    }

    private fun sendKey(code: Short, isDown: Boolean) {
        val isDownInt = when (isDown) {
            true -> 1
            else -> 0
        }
        hook.send_key(code, isDownInt)
    }
}