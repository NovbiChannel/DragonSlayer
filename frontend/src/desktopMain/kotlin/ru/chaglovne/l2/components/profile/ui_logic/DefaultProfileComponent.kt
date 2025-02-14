package ru.chaglovne.l2.components.profile.ui_logic

import com.arkivanov.decompose.ComponentContext
import java.awt.Desktop
import java.net.URI

class DefaultProfileComponent(
    componentContext: ComponentContext
): ProfileComponent, ComponentContext by componentContext {
    override fun openUrlInBrowser(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        } else {
            println("Desktop is not supported")
        }
    }
}