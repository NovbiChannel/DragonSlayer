import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import listeners.MouseListener
import org.junit.Test
import java.awt.Toolkit


class TestListeners {
    @Test
    fun mouseListener() = runBlocking {
        val listener = MouseListener { output ->
            when (output) {
                is InputType.KEYBOARD -> Unit
                is InputType.MOUSE -> { println(output.value) }
            }
        }
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeMouseListener(listener)
        while (true) {
            delay(DEFAULT_DELAY)
        }
    }

    @Test
    fun getImage() {
        val image = Toolkit.getDefaultToolkit().getImage("resources/dragon_slayer.png")
        println(image)
    }
}