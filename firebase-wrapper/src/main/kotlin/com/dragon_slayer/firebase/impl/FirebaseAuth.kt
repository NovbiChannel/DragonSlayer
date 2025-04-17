package com.dragon_slayer.firebase.impl

import com.dragon_slayer.firebase.FirebaseAuthRepository
import com.dragon_slayer.firebase.data.FirebaseProfile
import com.dragon_slayer.firebase.data.FirebaseUser
import com.dragon_slayer.firebase.data.FirebaseUserData
import com.dragon_slayer.firebase.data.isNotNull
import com.dragon_slayer.firebase.utils.*
import com.dragonslayer.AppConfig
import data.ApiConfig
import data.Profile
import data.User
import decodeToListMacro
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.util.*

class FirebaseAuth(appConfig: AppConfig): FirebaseAuthRepository {
    private val apiList: List<ApiConfig> = appConfig.getValue("api", ListSerializer(ApiConfig.serializer()))
        ?: throw IllegalArgumentException("API configuration section 'api' not found")

    private val apiConfig: ApiConfig = apiList.find { it.provider == "firebase" }
        ?: throw IllegalArgumentException("No Firebase config found in API list")

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            url(apiConfig.baseUrl)
        }
    }
    override suspend fun register(userName: String, password: String): Result<User, DataError> {
        if (!checkForUniqueness(userName)) {
            val userDataResult = handleResult(FirebaseUserData.serializer()) {
                client.put(Endpoints.userData(userName)) {
                    setBody(
                        FirebaseUserData(
                            userName = userName,
                            password = Encryption.encrypt(
                                plainText = password,
                                secretKey = Encryption.generateKey(userName)
                            )
                        )
                    )
                }
            }

            return when (userDataResult) {
                is Result.Error<*> -> userDataResult
                is Result.Success<*> -> {
                    handleResult(FirebaseUser.serializer()) {
                        client.put(Endpoints.user(userName)) {
                            setBody(
                                FirebaseUser(
                                    userName = userName,
                                    createdAt = System.currentTimeMillis()
                                )
                            )
                        }
                    }.map { it.toUser() }
                }
            }
        } else {
            return Result.Error(DataError.Status.USER_ALREADY_IS_EXIST)
        }
    }
    private suspend fun checkForUniqueness(userName: String): Boolean {
        val response = client.get(Endpoints.user(userName))
        return response.isNotNull()
    }

    override suspend fun authorization(userName: String, password: String): Result<User, DataError> {
        val userDataResult = handleResult(FirebaseUserData.serializer()) {
            client.get(Endpoints.userData(userName))
        }
        return when(userDataResult) {
            is Result.Error -> userDataResult
            is Result.Success -> {
                val userData = userDataResult.data
                val decryptPassword = Encryption.decrypt(
                    encryptedText = userData.password,
                    secretKey = Encryption.generateKey(userName)
                )

                if (password == decryptPassword) {
                    val userResult = handleResult(FirebaseUser.serializer()) {
                        client.get(Endpoints.user(userName))
                    }
                    when (userResult) {
                        is Result.Error -> userResult
                        is Result.Success -> Result.Success(userResult.data.toUser())
                    }
                } else {
                    Result.Error(DataError.Status.INVALID_PASSWORD)
                }
            }
        }
    }

    override suspend fun authentication(userId: String): Result<User, DataError> {
        return handleResult(FirebaseUser.serializer()) {
            client.get(Endpoints.user(userId))
        }.map { it.toUser() }
    }

    private suspend fun <T>handleResult(
        serializer: KSerializer<T>,
        call: suspend () -> HttpResponse
    ): Result<T, DataError> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = async { call() }.await()
            if (!response.isNotNull()) throw NullPointerException()
            println(response.bodyAsText())
            val data = Json.decodeFromString(serializer, response.bodyAsText())
            Result.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            val dataError = when(e) {
                is NullPointerException -> DataError.Status.USER_NOT_FOUND
                is SerializationException -> DataError.Remote.SERIALIZATION
                else -> DataError.Remote.UNKNOWN
            }
            Result.Error(dataError)
        }
    }

    private fun FirebaseUser.toUser(): User =
        User(
            userName = userName,
            avatarUrl = avatarUrl,
            profiles = profiles
                .map { it.key to it.value.toProfile() }
                .toMap()
        )

    private fun FirebaseProfile.toProfile(): Profile =
        Profile(
            macros = macros.decodeToListMacro()
        )
}