import io.ktor.client.*

object ApiClient: DragonSlayerAPI {
    private val client = HttpClient {
        contentNegotiation()
    }

    override fun getUserMacro(userId: Long): Macro {
        TODO("Not yet implemented")
    }

    override fun getAllUserMacros(userId: Long): List<Macro> {
        TODO("Not yet implemented")
    }
}