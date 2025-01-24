interface DragonSlayerAPI {
    suspend fun postAuthenticateParams(code: String, state: String, deviceId: String): Boolean
}