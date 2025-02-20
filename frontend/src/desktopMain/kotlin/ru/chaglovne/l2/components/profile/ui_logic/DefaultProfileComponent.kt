package ru.chaglovne.l2.components.profile.ui_logic

import ApiClient
import DragonSlayerAPI
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.flow.MutableSharedFlow
import java.awt.Desktop
import java.net.URI

class DefaultProfileComponent(
    componentContext: ComponentContext
): ProfileComponent, ComponentContext by componentContext {
    override val flow: MutableSharedFlow<DragonSlayerAPI.AuthData> = MutableSharedFlow()
    override val isUserAuth: MutableValue<Boolean> = MutableValue(false)

    override fun openUrlInBrowser(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        } else {
            println("Desktop is not supported")
        }
    }

    override suspend fun postAuthParams(code: String, state: String, deviceId: String) {
        val userInfo = ApiClient.postAuthenticateParams(code, state, deviceId)
        val authData = userInfo?.let {
            DragonSlayerAPI.AuthData(DragonSlayerAPI.DataType.AuthSuccess, it.user.toString())
        }?: DragonSlayerAPI.AuthData(DragonSlayerAPI.DataType.AuthError, "auth data is null")
        flow.emit(authData)
    }

    override suspend fun getAuthUrl() {
        val url = ApiClient.getAuthenticateUrl()?: return
        flow.emit(DragonSlayerAPI.AuthData(DragonSlayerAPI.DataType.SendAuthUrl, url))
    }
}