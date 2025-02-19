import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

object ApiClient: DragonSlayerAPI {
    private val client = HttpClient(engineFactory()) {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        contentNegotiation(json)
        websocketConfig(json)
    }

    override suspend fun postAuthenticateParams(code: String, state: String, deviceId: String): Boolean {
        return try {
            val response = client.post("http://185.65.200.31:8080/auth/callback") {
                parameter(ApiParams.CODE, code)
                parameter(ApiParams.STATE, state)
                parameter(ApiParams.DEVICE_ID, deviceId)
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    override suspend fun wsAuthenticateFlow(flow: MutableSharedFlow<DragonSlayerAPI.AuthData>) {
        client.webSocket("ws://localhost:8080/ws/auth") {
            while (isActive) {
                val receive = receiveDeserialized<DragonSlayerAPI.WsDataReceive>()
                val type = receive.type.stringToDataType()
                flow.emit(DragonSlayerAPI.AuthData(type, receive.data))
            }
        }
    }
}