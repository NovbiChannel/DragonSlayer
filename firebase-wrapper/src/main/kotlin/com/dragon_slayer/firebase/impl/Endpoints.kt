package com.dragon_slayer.firebase.impl

internal object Endpoints {
    private const val USERS = "/users"
    private const val USERS_DATA = "/users_data"
    const val GET_ALL_USERS_DATA = "$USERS_DATA.json"

    fun user(userName: String): String {
        return "$USERS/$userName.json"
    }
    fun userData(userName: String): String {
        return "$USERS_DATA/$userName.json"
    }
}