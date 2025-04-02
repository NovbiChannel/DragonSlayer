package data

import Macro

data class User(
    val userName: String,
    val avatarUrl: String? = null,
    val profiles: Map<String, Profile> = emptyMap()
)
data class Profile(
    val macros: List<Macro>
)