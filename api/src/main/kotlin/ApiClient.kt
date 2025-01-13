import io.ktor.client.*
import io.ktor.client.engine.cio.*

object ApiClient: DragonSlayerAPI {
    private val client = HttpClient(CIO) {
        contentNegotiation()
    }

    override fun getUserMacro(userId: Long): Macro {
        TODO("Not yet implemented")
    }

    override fun getAllUserMacros(userId: Long): List<Macro> {
        TODO("Not yet implemented")
    }
}