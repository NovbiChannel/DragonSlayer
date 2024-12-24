import eu.vendeli.tgbot.TelegramBot

suspend fun main() {
    val bot = TelegramBot("")
    bot.handleUpdates()
}