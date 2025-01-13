data class Macro(
    val title: String,
    val isPublic: Boolean = false,
    val description: String,
    val comment: String,
    val inputType: InputType,
    val loopType: LoopType,
    val keys: List<EventConfig>
)

sealed class LoopType {
    data object SINGLE: LoopType()
    data object INFINITE: LoopType()
    data class CUSTOM(val repetitions: Int): LoopType()
}

sealed class InputType {
    data class KEYBOARD(val value: Int): InputType()
    data class MOUSE(val value: Int): InputType()
}

data class EventConfig(
    val eventKey: Int,
    val delay: Long = DEFAULT_DELAY,
    val interval: Long = 0L
)
