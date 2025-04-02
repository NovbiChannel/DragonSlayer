package com.dragon_slayer.firebase.utils

sealed interface DataError: Error {
    enum class Remote: DataError {
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN
    }
    enum class Status: DataError {
        INVALID_PASSWORD,
        USER_NOT_FOUND,
        USER_ALREADY_IS_EXIST,
    }
}

fun DataError.toMessage(): String {
    return when (this) {
        DataError.Remote.NO_INTERNET -> "Нет активного интернет соединения"
        DataError.Status.USER_NOT_FOUND -> "Пользователь с таким логином не найден"
        DataError.Status.USER_ALREADY_IS_EXIST -> "Пользователь с таким логином уже существует"
        DataError.Status.INVALID_PASSWORD -> "Введён не верный пароль"
        else -> "Упс... Что-то пошло не так. Попробуй позже"
    }
}