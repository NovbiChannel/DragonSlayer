package notification

import java.awt.AWTException
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.io.File

val currentPath = File(".").absolutePath
val tray = SystemTray.getSystemTray()
val trayIcon = TrayIcon(Toolkit.getDefaultToolkit().getImage("${currentPath}core/src/main/resources/app_icon.png"), "Заголовок уведомления")
fun sendNotification(message: String) {
    println(currentPath)
    if (!SystemTray.isSupported()) {
        println("Системные уведомления не поддерживаются.")
        return
    }

    try {
        tray.add(trayIcon)
        trayIcon.displayMessage("Сообщение", message, TrayIcon.MessageType.INFO)
    } catch (e: AWTException) {
        println("Не удалось добавить иконку в системный трей.")
        e.printStackTrace()
    }
}