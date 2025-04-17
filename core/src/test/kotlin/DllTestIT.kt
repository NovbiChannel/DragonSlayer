import com.sun.jna.Library
import com.sun.jna.Native
import org.junit.Test

class DllTestIT {
    interface TestLibrary: Library {
        companion object {
            val INSTANCE: TestLibrary = Native.load(
                "dragonlib_event_hook", TestLibrary::class.java
            )
        }

        fun dragonlib_event_hook()
    }
    private val dragonlib = TestLibrary.INSTANCE
    @Test
    fun dllUseTest() {
        dragonlib.dragonlib_event_hook()
    }
}