package natives

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

interface InterceptionLibrary : Library {
    companion object {
        val INSTANCE: InterceptionLibrary = Native.load(
            "libdragon_interception", InterceptionLibrary::class.java
        )
    }

    fun interception_create_context(): Pointer
    fun interception_destroy_context(context: Pointer)
    fun interception_send(context: Pointer, device: Int, stroke: Pointer, nstroke: Int): Int
    fun interception_is_keyboard(device: Int): Boolean
    fun interception_is_mouse(device: Int): Boolean
}