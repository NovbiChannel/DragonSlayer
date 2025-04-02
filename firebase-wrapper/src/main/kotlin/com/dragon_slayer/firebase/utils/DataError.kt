package com.dragon_slayer.firebase.utils

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        BAD_REQUEST,
        UNKNOWN
    }
    enum class Status: DataError {
        USER_NOT_FOUND,
        USER_ALREADY_IS_EXIST,
    }
}
