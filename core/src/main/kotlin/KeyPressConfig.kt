data class EventConfig(
    val eventKey: Int,
    val delay: Long = DEFAULT_DELAY,
    val interval: Long = 0L
)