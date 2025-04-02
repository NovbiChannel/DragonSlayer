package data

import Macro
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userName: String,
    val avatarUrl: String? = null,
    val profiles: Map<String, Profile> = emptyMap()
)

@Serializable
data class Profile(
    val macros: List<Macro>
)