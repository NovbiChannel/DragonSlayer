package ui

import androidx.compose.runtime.Composable
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.url.URL

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
        Img(
            attrs = {
                style {
                    width(100.px)
                    height(100.px)
                }
            },
            src = "https://firebasestorage.googleapis.com/v0/b/beauty-admin-panel.appspot.com/o/dragon.png?alt=media&token=caf0a6d5-3c15-4bc4-8486-8371edd6453b"
        )

        if (state != null && deviceId != null && code != null) {
            H1(
                attrs = {
                    style {
                        fontSize(24.px)
                        color(Color.white)
                        textAlign("center")
                        padding(0.px)
                        margin(0.px)
                    }
                }
            ) {
                Text("Авторизация прошла успешно!")
            }
            P(
                attrs = {
                    style {
                        fontSize(12.px)
                        color(Color.white)
                        textAlign("center")
                        padding(0.px)
                        margin(0.px)
                    }
                }
            ) {
                Text("Пожалуйста, вернитесь в приложение, чтобы продолжить.")
            }
        } else {
            H1(
                attrs = {
                    style {
                        fontSize(24.px)
                        color(Color.white)
                        textAlign("center")
                        padding(0.px)
                        margin(0.px)
                    }
                }
            ) {
                Text("Параметр не передан")
            }
        }
    }
}