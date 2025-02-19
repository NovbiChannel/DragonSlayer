import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.Serializable

interface DragonSlayerAPI {
    suspend fun postAuthenticateParams(code: String, state: String, deviceId: String): Boolean
    suspend fun wsAuthenticateFlow(flow: MutableSharedFlow<AuthData>)
    @Serializable
    data class WsDataReceive(
        val type: String,
        val data: String
    )
    data class AuthData(
        val type: DataType,
        val data: String
    )
    sealed class DataType {
        data object SendAuthUrl: DataType()
        data object AuthSuccess: DataType()
        data object AuthError: DataType()
        data object UnknownType: DataType()
    }
    fun String.stringToDataType(): DataType {
        return when (this) {
            "send_auth_url" -> DataType.SendAuthUrl
            "auth_success" -> DataType.AuthSuccess
            "auth_error" -> DataType.AuthError
            else -> DataType.UnknownType
        }
    }
}