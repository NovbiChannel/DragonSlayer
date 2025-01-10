const val DEFAULT_DELAY = 34L
val Int.sec: Long
    get() = this * 1_000L
val Int.min: Long
    get() = this * 60_000L