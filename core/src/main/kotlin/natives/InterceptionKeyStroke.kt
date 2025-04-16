package natives

import com.sun.jna.Structure

@Structure.FieldOrder("code", "state", "information")
class InterceptionKeyStroke : Structure() {
    @JvmField var code: Short = 0
    @JvmField var state: Short = 0
    @JvmField var information: Int = 0
}