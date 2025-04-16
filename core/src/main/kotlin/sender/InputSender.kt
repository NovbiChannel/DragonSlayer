package sender

interface InputSender {
    fun keyDown(keyCode: Int)
    fun keyUp(keyCode: Int)
    fun mouseDown(keyCode: Int)
    fun mouseUp(keyCode: Int)
}