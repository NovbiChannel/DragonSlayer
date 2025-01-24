import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

object ApiClient: DragonSlayerAPI {
    private val client = HttpClient(engineFactory()) {
        contentNegotiation()
        defaultRequest { url("http://185.65.200.31:8080") }
    }

    override suspend fun postAuthenticateParams(code: String, state: String, deviceId: String): Boolean {
        return try {
            val response = client.post("/auth/callback") {
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
}