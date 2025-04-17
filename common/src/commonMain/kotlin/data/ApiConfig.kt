package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiConfig(
    val provider: String,
    @SerialName("base_url") val baseUrl: String
)
