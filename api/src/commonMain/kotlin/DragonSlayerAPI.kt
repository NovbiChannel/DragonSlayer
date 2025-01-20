interface DragonSlayerAPI {
    fun getUserMacro(userId: Long): Macro
    fun getAllUserMacros(userId: Long): List<Macro>
    suspend fun postAuthenticateParams(code: String, state: String, deviceId: String): Boolean
}