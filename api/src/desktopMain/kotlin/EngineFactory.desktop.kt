import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

actual fun engineFactory(): HttpClientEngineFactory<*> = CIO