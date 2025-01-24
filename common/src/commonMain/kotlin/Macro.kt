import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Macro(
    var id: Int = 0,
    val title: String,
    val isPublic: Boolean = false,
    val description: String? = null,
    val inputType: InputType,
    val loopType: LoopType,
    val events: List<EventType>
)

@Serializable
@SerialName("event")
sealed class EventType {
    @Serializable
    @SerialName("delay")
    data class Delay(@SerialName("delay_TU") val timeUnit: TimeUnit = TimeUnit.Millisecond(DEFAULT_DELAY.toInt())): EventType()
    @Serializable
    @SerialName("key_press")
    data class KeyPress(@SerialName("key_press_value") val key: Int, @SerialName("key_press_TU") var timeUnit: TimeUnit? = null): EventType()
    @Serializable
    @SerialName("key_release")
    data class KeyRelease(@SerialName("key_release_value") val key: Int): EventType()
    @Serializable
    @SerialName("mouse_press")
    data class MousePress(@SerialName("mouse_press_value") val key: Int, @SerialName("mouse_press_TU") var timeUnit: TimeUnit? = null): EventType()
    @Serializable
    @SerialName("mouse_release")
    data class MouseRelease(val key: Int): EventType()
}

@Serializable
@SerialName("loop_type")
sealed class LoopType {
    @Serializable
    @SerialName("single")
    data object SINGLE: LoopType()
    @Serializable
    @SerialName("infinite")
    data object INFINITE: LoopType()
    @Serializable
    @SerialName("custom")
    data class CUSTOM(@SerialName("repetitions") val repetitions: Int): LoopType()
}

@Serializable
@SerialName("input_type")
sealed class InputType {
    @Serializable
    @SerialName("keyboard")
    data class KEYBOARD(@SerialName("keyboard_value") val value: Int): InputType()
    @Serializable
    @SerialName("mouse")
    data class MOUSE(@SerialName("mouse_value") val value: Int): InputType()
}