package natives

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

interface InterceptionLibrary : Library {
    companion object {
        val INSTANCE: InterceptionLibrary = Native.load(
            "dragonlib_event_hook", InterceptionLibrary::class.java
        )
    }

    fun init_interception(): Int
    fun send_key(code: Short, is_down: Int)
    fun release_interception()
}