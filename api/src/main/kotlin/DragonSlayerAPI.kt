interface DragonSlayerAPI {
    fun getUserMacro(userId: Long): Macro
    fun getAllUserMacros(userId: Long): List<Macro>
}