import org.jetbrains.compose.web.renderComposable
import ui.RootUI

fun main() {
    renderComposable("root") {
        RootUI()
    }
}