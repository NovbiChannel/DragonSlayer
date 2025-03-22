import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import models.UserInfoResponse

object ApiClient: DragonSlayerAPI {
    private val client = HttpClient(engineFactory()) {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        contentNegotiation(json)
        websocketConfig(json)
    }

    override suspend fun getAuthenticateUrl(): String? {
        return try {
            val response = client.get("http://192.168.0.106:8080/v1/authenticate/url") {
                parameter("provider", "vk_id")
            }
            response.body<String>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun postAuthenticateParams(code: String, state: String, deviceId: String): UserInfoResponse? {
        return try {
            val response = client.post("http://192.168.0.106:8080/v1/authenticate/callback") {
                parameter("provider", "vk_id")
                parameter(ApiParams.CODE, code)
                parameter(ApiParams.STATE, state)
                parameter(ApiParams.DEVICE_ID, deviceId)
            }
            response.body<UserInfoResponse>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}