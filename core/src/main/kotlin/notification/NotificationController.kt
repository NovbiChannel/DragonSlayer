package notification

import java.awt.*
import java.io.File

var trayIcon: TrayIcon? = null

fun sendNotification(message: String) {
    if (!SystemTray.isSupported()) {
        println("Системные уведомления не поддерживаются.")
        return
    }

    try {
        val currentPath = File(".").absolutePath
        val trayIconImage = Toolkit.getDefaultToolkit().getImage("$currentPath/core/src/main/resources/app_icon.png")

        if (trayIcon != null) {
            val tray = SystemTray.getSystemTray()
            tray.remove(trayIcon)
        }

        trayIcon = TrayIcon(trayIconImage, "Dragon Slayer")
        trayIcon?.isImageAutoSize = true

        val tray = SystemTray.getSystemTray()
        tray.add(trayIcon)

        trayIcon?.displayMessage("Сообщение", message, TrayIcon.MessageType.INFO)

    } catch (e: AWTException) {
        println("Не удалось добавить иконку в системный трей.")
        e.printStackTrace()
    }
}