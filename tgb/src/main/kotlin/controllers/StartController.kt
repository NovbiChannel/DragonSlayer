package controllers

import markups.botFeatures
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User

class StartController {
    @CommandHandler(["/start"])
    suspend fun start(user: User, bot: TelegramBot) {
        message("Фичи:\n1) Отключение ПК\n2) Скриншот экрана")
            .markup(botFeatures)
            .send(user, bot)
    }
}