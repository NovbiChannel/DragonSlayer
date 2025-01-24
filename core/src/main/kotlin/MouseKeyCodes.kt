object MouseKeyCodes {
    const val BML = 1
    const val BMR = 2
    const val MB4 = 4
    const val MB5 = 5

    fun getKeyName(key: Int): String {
        return when (key) {
            1 -> "LMB"
            2 -> "RMB"
            4 -> "MB4"
            5 -> "MB5"
            else -> throw IllegalArgumentException("Неизвестный код клавиши: $key")
        }
    }
}