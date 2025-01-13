import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object ApiClient: DragonSlayerAPI {
    private val client = HttpClient(CIO) {
        followRedirects = true
        contentNegotiation()
    }

    suspend fun interceptUrl(url: String): String {
        return try {
            val response: HttpResponse = client.get(url)
            response.request.url.toString()
        } catch (e: Exception) {
            println("Error fetching URL: ${e.message}")
            "Error: ${e.message}"
        } finally {
            client.close()
        }
    }

    override fun getUserMacro(userId: Long): Macro {
        TODO("Not yet implemented")
    }

    override fun getAllUserMacros(userId: Long): List<Macro> {
        TODO("Not yet implemented")
    }
}