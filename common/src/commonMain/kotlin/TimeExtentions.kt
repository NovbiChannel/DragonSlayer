import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val DEFAULT_DELAY = 100L
val Int.sec: Long
    get() = this * 1_000L
val Int.min: Long
    get() = this * 60_000L

@Serializable
sealed class TimeUnit {
    @Serializable
    data class Millisecond(@SerialName("milliseconds_value") override var value: Int): TimeUnit()
    @Serializable
    data class Seconds(@SerialName("seconds_value") override var value: Int): TimeUnit()
    @Serializable
    data class Minutes(@SerialName("minutes_value") override var value: Int): TimeUnit()

    companion object {
        const val DEFAULT_VALUE = 100
    }
    open var value: Int = DEFAULT_VALUE

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