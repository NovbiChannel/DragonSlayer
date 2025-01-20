package ui

import ApiClient
import ApiParams
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import errorSrc
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.url.URL
import successSrc

@Composable
fun RootUI() {
    val url = URL(window.location.href)
    val params = url.searchParams

    val code = params.get(ApiParams.CODE)
    val state = params.get(ApiParams.STATE)
    val deviceId = params.get(ApiParams.DEVICE_ID)

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

        LaunchedEffect(true) {
            if (state != null && deviceId != null && code != null) {
                if (ApiClient.postAuthenticateParams(code, state, deviceId)) {
                    Success()
                } else {
                    Error("Упс... Что-то пошло не так, проверь консоль")
                }
            } else {
                Error("Упс... Что-то пошло не так, проверь консоль")
                println("Receive params contains is null:\nstate=$state\ndeviceId=$deviceId\ncode=$code")
            }
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