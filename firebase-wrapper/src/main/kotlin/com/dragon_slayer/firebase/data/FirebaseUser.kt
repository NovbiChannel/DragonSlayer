package com.dragon_slayer.firebase.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FirebaseUser(
    @SerialName("user_name") val userName: String,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("avatar_url") val avatarUrl: String = NO_DATA,
    val profiles: Map<String, FirebaseProfile> = emptyMap()
)

@Serializable
internal data class FirebaseUserData(
    @SerialName("user_name") val userName: String,
    val password: String
)

@Serializable
internal data class FirebaseProfile(
    @SerialName("macro") val macros: String
)