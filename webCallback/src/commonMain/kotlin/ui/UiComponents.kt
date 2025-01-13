package ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun H1(text: String) {
    org.jetbrains.compose.web.dom.H1(
        attrs = {
            style {
                fontSize(24.px)
                color(Color.white)
                textAlign("center")
                padding(0.px)
                margin(0.px)
            }
        }
    ) { Text(text) }
}

@Composable
fun P(text: String) {
    org.jetbrains.compose.web.dom.P(
        attrs = {
            style {
                fontSize(12.px)
                color(Color.white)
                textAlign("center")
                padding(0.px)
                margin(0.px)
            }
        }
    ) { Text(text) }
}

@Composable
fun SrcImage(src: String) {
    Img(
        attrs = {
            style {
                width(100.px)
                height(100.px)
            }
        },
        src = src
    )
}