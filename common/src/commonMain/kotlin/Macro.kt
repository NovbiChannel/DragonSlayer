import kotlinx.serialization.Serializable

@Serializable
data class Macro(
    val title: String,
    val isPublic: Boolean = false,
    val description: String? = null,
    val inputType: InputType,
    val loopType: LoopType,
    val events: List<EventType>
)

@Serializable
sealed class EventType {
    @Serializable
    data class Delay(val timeUnit: TimeUnit = TimeUnit.Millisecond(DEFAULT_DELAY.toInt())): EventType()
    @Serializable
    data class KeyPress(val key: Int, var timeUnit: TimeUnit? = null): EventType()
    @Serializable
    data class KeyRelease(val key: Int): EventType()
    @Serializable
    data class MousePress(val key: Int, var timeUnit: TimeUnit? = null): EventType()
    @Serializable
    data class MouseRelease(val key: Int): EventType()
}

@Serializable
sealed class LoopType {
    @Serializable
    data object SINGLE: LoopType()
    @Serializable
    data object INFINITE: LoopType()
    @Serializable
    data class CUSTOM(val repetitions: Int): LoopType()
}

@Serializable
sealed class InputType {
    @Serializable
    data class KEYBOARD(val value: Int): InputType()
    @Serializable
    data class MOUSE(val value: Int): InputType()
}