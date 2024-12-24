package callbacks

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommonHandler
import eu.vendeli.tgbot.annotations.InputHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TurnOffCallback {
    companion object {
        const val TURN_OFF = "Отключить компьютер"
        const val PASSWORD = "password"
    }

    @CommonHandler.Regex(TURN_OFF)
    suspend fun turnOff(user: User, bot: TelegramBot) {
        message("Введите пароль").send(user, bot)
        bot.inputListener[user] = PASSWORD
    }

    @InputHandler([PASSWORD])
    suspend fun checkAuth(update: ProcessedUpdate, user: User, bot: TelegramBot) {
        val password = update.text
        if (password == "Novbimail2011") {
            val completionMessage = turnOffPc()
            message(completionMessage).send(user, bot)
        } else {
            message("Неверный пароль")
        }
    }
}

private fun turnOffPc(): String {
    val command = listOf("shutdown", "/s", "/t", "0")
    return try {
        val process = ProcessBuilder(command).start()
        process.waitFor()
        "Компьютер отключен"
    } catch (e: IOException) {
        e.printStackTrace()
        e.message?: "IO Exception error"
    } catch (e: InterruptedException) {
        e.printStackTrace()
        e.message?: "Interrupted Exception error"
    }
}