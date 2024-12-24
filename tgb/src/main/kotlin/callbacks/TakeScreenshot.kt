package callbacks

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommonHandler
import eu.vendeli.tgbot.api.media.sendDocument
import eu.vendeli.tgbot.api.media.sendPhoto
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.utils.toImplicitFile
import java.awt.AWTException
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TakeScreenshot {
    companion object {
        const val TAKE_SCREENSHOT = "Прислать скриншот экрана"
    }

    @CommonHandler.Regex(TAKE_SCREENSHOT)
    suspend fun takeScreenshot(user: User, bot: TelegramBot) {
        try {
            // Получаем размер экрана
            val screenSize = Toolkit.getDefaultToolkit().screenSize
            val rectangle = Rectangle(screenSize)

            // Создаем объект Robot для захвата экрана
            val robot = Robot()
            val screenFullImage: BufferedImage = robot.createScreenCapture(rectangle)
            val outputFile = File("${System.currentTimeMillis()}.png")
            ImageIO.write(screenFullImage, "png", outputFile)

            sendPhoto(outputFile.toImplicitFile("${System.currentTimeMillis()}.png")).send(user, bot)
            outputFile.delete()
        } catch (ex: AWTException) {
            message(ex.message?: "Ошибка при создании скриншота").send(user, bot)
        } catch (ex: Exception) {
            message(ex.message?: "Ошибка").send(user, bot)
        }
    }
}