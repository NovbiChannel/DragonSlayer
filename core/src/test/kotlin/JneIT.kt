import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import sender.DefaultInputSender
import sender.InputSender
import sender.InterceptionInputSender

class JneIT {
    private val nativeSender = InterceptionInputSender()
    private val sender = DefaultInputSender()
    private val keyCodeA = 0x41

    @Test
    fun loopA(): Unit = runBlocking {
        println("Начинаем тестовое нажатие клавиши A...")
        testKeyLoop(sender, keyCodeA, repeatCount = 5, delayMillis = 300)
        println("Тест завершён.")
    }

    private suspend fun testKeyLoop(sender: InputSender, keyCode: Int, repeatCount: Int = 10, delayMillis: Long = 200) {
        repeat(repeatCount) {
            sender.keyDown(keyCode)
            delay(50)
            sender.keyUp(keyCode)
            delay(delayMillis)
        }
    }

    @Test
    fun t1(): Unit = runBlocking {
        testKeyLoop(nativeSender, keyCodeA)
    }

    @Test
    fun loopRightMouseClick(): Unit = runBlocking {
        println("Начинаем тестовое нажатие клавиши A...")
        testMouseLoop(sender, 1, repeatCount = 10, delayMillis = 300)
        println("Тест завершён.")
    }

    private suspend fun testMouseLoop(sender: InputSender, keyCode: Int, repeatCount: Int = 10, delayMillis: Long = 200) {
        repeat(repeatCount) {
            sender.mouseDown(keyCode)
            delay(50)
            sender.mouseUp(keyCode)
            delay(delayMillis)
        }
    }
}