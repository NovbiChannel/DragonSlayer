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

    override suspend fun wsConnect() = ApiClient.wsAuthenticateFlow(flow)
}