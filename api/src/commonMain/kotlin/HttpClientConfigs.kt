import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun HttpClientConfig<*>.contentNegotiation(json: Json) {
    install(ContentNegotiation) {
        json(json)
    }
}
fun HttpClientConfig<*>.websocketConfig(json: Json) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(json)
    }
}