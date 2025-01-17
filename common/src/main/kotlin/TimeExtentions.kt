const val DEFAULT_DELAY = 34L
val Int.sec: Long
    get() = this * 1_000L
val Int.min: Long
    get() = this * 60_000L

sealed class TimeUnit {
    data class Millisecond(val value: Int): TimeUnit()
    data class Seconds(val value: Int): TimeUnit()
    data class Minutes(val value: Int): TimeUnit()

    fun delay(): Long {
        return when (val timeUnit = this) {
            is Millisecond -> timeUnit.value.toLong()
            is Minutes -> timeUnit.value.min
            is Seconds -> timeUnit.value.sec
        }
    }

    fun getName(): String {
        return when (this) {
            is Millisecond -> "мс"
            is Minutes -> "мин"
            is Seconds -> "сек"
        }
    }
}