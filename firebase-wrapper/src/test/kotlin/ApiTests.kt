import com.dragon_slayer.firebase.data.FirebaseUser
import com.dragon_slayer.firebase.data.FirebaseUserData
import com.dragon_slayer.firebase.data.isNotNull
import com.dragon_slayer.firebase.impl.Endpoints
import com.dragon_slayer.firebase.impl.FirebaseAuth
import com.dragon_slayer.firebase.utils.Encryption
import com.dragon_slayer.firebase.utils.onError
import com.dragon_slayer.firebase.utils.onSuccess
import data.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ApiTests {
    private val firebaseAuth = FirebaseAuth()
    private val userId = "9968cabdd6ac420f82da9efb7745b873"
    private val userName = "novbichannel"
    private val password = "Novbimail2011"
    private val client = HttpClient(CIO) {
        defaultRequest {
            url("https://dragon-slayer-1d808-default-rtdb.asia-southeast1.firebasedatabase.app/")
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    @Test
    fun register(): Unit = runBlocking {
        firebaseAuth
            .register(
                userName = userName,
                password = password
            )
            .onSuccess { userId ->
                println("success! \n UserID - $userId")
            }
            .onError { error ->
                println(error.toString())
            }
    }
    @Test
    fun authorization(): Unit = runBlocking {
        firebaseAuth
            .authorization(
                userName = userName,
                password = password
            )
            .onSuccess { user: User ->
                println("Success authorization: $user")
            }
            .onError { error ->
                println(error.toString())
            }
    }
    @Test
    fun authentication(): Unit = runBlocking {
        firebaseAuth
            .authentication(
                userId = userId
            )
            .onSuccess { user ->
                println("Success authentication: ${user.userName}")
            }
            .onError { error ->
                println(error.toString())
            }
    }

    @Test
    fun testResponse() = runBlocking {
        val userResponse = client
            .put(Endpoints.user(userId)) {
                setBody(FirebaseUser(
                    userName = userName,
                    createdAt = System.currentTimeMillis()
                ))
            }
        val userDataResponse = client
            .put(Endpoints.userData(userId)) {
                setBody(FirebaseUserData(
                    userName = userName,
                    password = password
                ))
            }
        println(userResponse.bodyAsText())
        println(userDataResponse.bodyAsText())
    }
}