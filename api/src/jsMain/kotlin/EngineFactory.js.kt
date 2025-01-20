import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

actual fun engineFactory(): HttpClientEngineFactory<*> = Js