package ui

import androidx.compose.runtime.Composable
import errorSrc
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.url.URL
import successSrc

@Composable
fun RootUI() {
    val url = URL(window.location.href)
    val params = url.searchParams

    val code = params.get("code")
    val state = params.get("state")
    val deviceId = params.get("device_id")

    Div(
        attrs = {
            style {
                width(100.percent)
                height(100.percent)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.Center)
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                gap(10.px)
                backgroundColor(Color("#13171E"))
            }
        }
    ) {
        if (state != null && deviceId != null && code != null) {
            Success()
        } else {
            Error("Упс... Что-то пошло не так, проверь консоль")
            println("receive params:\nstate=$state\ndeviceId=$deviceId\ncode=$code")
        }
    }
}

@Composable
fun Success() {
    SrcImage(successSrc)
    H1("Авторизация прошла успешно!")
    P("Пожалуйста, вернитесь в приложение, чтобы продолжить.")
}

@Composable
fun Error(message: String) {
    SrcImage(errorSrc)
    H1(message)
}