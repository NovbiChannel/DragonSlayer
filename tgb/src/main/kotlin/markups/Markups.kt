package markups

import callbacks.TakeScreenshot
import callbacks.TurnOffCallback
import eu.vendeli.tgbot.types.keyboard.KeyboardButton
import eu.vendeli.tgbot.types.keyboard.ReplyKeyboardMarkup

val botFeatures = ReplyKeyboardMarkup(
    listOf(listOf(
        KeyboardButton(TurnOffCallback.TURN_OFF),
        KeyboardButton(TakeScreenshot.TAKE_SCREENSHOT)
    )), resizeKeyboard = true
)