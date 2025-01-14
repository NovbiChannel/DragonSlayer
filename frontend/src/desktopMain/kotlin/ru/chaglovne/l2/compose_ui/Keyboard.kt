package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import l2macros.frontend.generated.resources.*
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.arrow_left
import l2macros.frontend.generated.resources.arrow_up
import l2macros.frontend.generated.resources.mingcute_windows_fill
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.theme.Colors
import java.awt.event.KeyEvent

data class KeyButtonModel(
    val title: String,
    val keyCode: Int,
    val height: Int = 34,
    val weight: Float? = null,
    val resource: DrawableResource? = null
)

const val SMALL_BUTTON = 20

private val keyList = listOf(
    listOf(
        KeyButtonModel("esc", KeyEvent.VK_ESCAPE, height = SMALL_BUTTON, weight = 1f),
        KeyButtonModel("f1", KeyEvent.VK_F1, height = SMALL_BUTTON),
        KeyButtonModel("f2", KeyEvent.VK_F2, height = SMALL_BUTTON),
        KeyButtonModel("f3", KeyEvent.VK_F3, height = SMALL_BUTTON),
        KeyButtonModel("f4", KeyEvent.VK_F4, height = SMALL_BUTTON),
        KeyButtonModel("f5", KeyEvent.VK_F5, height = SMALL_BUTTON),
        KeyButtonModel("f6", KeyEvent.VK_F6, height = SMALL_BUTTON),
        KeyButtonModel("f7", KeyEvent.VK_F7, height = SMALL_BUTTON),
        KeyButtonModel("f8", KeyEvent.VK_F8, height = SMALL_BUTTON),
        KeyButtonModel("f9", KeyEvent.VK_F9, height = SMALL_BUTTON),
        KeyButtonModel("f10", KeyEvent.VK_F10, height = SMALL_BUTTON),
        KeyButtonModel("f11", KeyEvent.VK_F11, height = SMALL_BUTTON),
        KeyButtonModel("f12", KeyEvent.VK_F12, height = SMALL_BUTTON),
        KeyButtonModel("del", KeyEvent.VK_DELETE, height = SMALL_BUTTON),
    ),
    listOf(
        KeyButtonModel("`", KeyEvent.VK_BACK_QUOTE),
        KeyButtonModel("1", KeyEvent.VK_1),
        KeyButtonModel("2", KeyEvent.VK_2),
        KeyButtonModel("3", KeyEvent.VK_3),
        KeyButtonModel("4", KeyEvent.VK_4),
        KeyButtonModel("5", KeyEvent.VK_5),
        KeyButtonModel("6", KeyEvent.VK_6),
        KeyButtonModel("7", KeyEvent.VK_7),
        KeyButtonModel("8", KeyEvent.VK_8),
        KeyButtonModel("9", KeyEvent.VK_9),
        KeyButtonModel("0", KeyEvent.VK_0),
        KeyButtonModel("-", KeyEvent.VK_MINUS),
        KeyButtonModel("=", KeyEvent.VK_PLUS),
        KeyButtonModel("backspace", KeyEvent.VK_BACK_SPACE, weight = 1f),
    ),
    listOf(
        KeyButtonModel("TAB", KeyEvent.VK_TAB, weight = 1f),
        KeyButtonModel("Q", KeyEvent.VK_Q),
        KeyButtonModel("W", KeyEvent.VK_W),
        KeyButtonModel("E", KeyEvent.VK_E),
        KeyButtonModel("R", KeyEvent.VK_R),
        KeyButtonModel("T", KeyEvent.VK_T),
        KeyButtonModel("Y", KeyEvent.VK_Y),
        KeyButtonModel("U", KeyEvent.VK_U),
        KeyButtonModel("I", KeyEvent.VK_I),
        KeyButtonModel("O", KeyEvent.VK_O),
        KeyButtonModel("P", KeyEvent.VK_P),
        KeyButtonModel("[", KeyEvent.VK_BRACELEFT),
        KeyButtonModel("]", KeyEvent.VK_BRACERIGHT),
        KeyButtonModel("\\", KeyEvent.VK_BACK_SLASH),
    ),
    listOf(
        KeyButtonModel("Caps Lock", KeyEvent.VK_CAPS_LOCK, weight = 1f),
        KeyButtonModel("A", KeyEvent.VK_A),
        KeyButtonModel("S", KeyEvent.VK_S),
        KeyButtonModel("D", KeyEvent.VK_D),
        KeyButtonModel("F", KeyEvent.VK_F),
        KeyButtonModel("G", KeyEvent.VK_G),
        KeyButtonModel("H", KeyEvent.VK_H),
        KeyButtonModel("J", KeyEvent.VK_J),
        KeyButtonModel("K", KeyEvent.VK_K),
        KeyButtonModel("L", KeyEvent.VK_L),
        KeyButtonModel(";", KeyEvent.VK_COLON),
        KeyButtonModel("'", KeyEvent.VK_DEAD_GRAVE),
        KeyButtonModel("Enter", KeyEvent.VK_ENTER, weight = 1f),
    ),
    listOf(
        KeyButtonModel("Shift", KeyEvent.VK_SHIFT, weight = 1f),
        KeyButtonModel("Z", KeyEvent.VK_Z),
        KeyButtonModel("X", KeyEvent.VK_X),
        KeyButtonModel("C", KeyEvent.VK_C),
        KeyButtonModel("V", KeyEvent.VK_V),
        KeyButtonModel("B", KeyEvent.VK_B),
        KeyButtonModel("N", KeyEvent.VK_N),
        KeyButtonModel("M", KeyEvent.VK_M),
        KeyButtonModel(",", KeyEvent.VK_COMMA),
        KeyButtonModel(".", KeyEvent.VK_COMMA),
        KeyButtonModel("/", KeyEvent.VK_SLASH),
        KeyButtonModel("Shift", KeyEvent.VK_SHIFT, weight = 1f),
    ),
    listOf(
        KeyButtonModel("Ctrl", KeyEvent.VK_CONTROL, weight = 1f),
        KeyButtonModel("Win", KeyEvent.VK_WINDOWS, resource = Res.drawable.mingcute_windows_fill),
        KeyButtonModel("Alt", KeyEvent.VK_ALT),
        KeyButtonModel("Space", KeyEvent.VK_SPACE, weight = 2f),
        KeyButtonModel("Alt", KeyEvent.VK_ALT),
        KeyButtonModel("Ctrl", KeyEvent.VK_CONTROL),
        KeyButtonModel("Left", KeyEvent.VK_LEFT, resource = Res.drawable.arrow_left),
        KeyButtonModel("Up", KeyEvent.VK_UP, resource = Res.drawable.arrow_up),
        KeyButtonModel("Down", KeyEvent.VK_DOWN, resource = Res.drawable.arrow_down),
        KeyButtonModel("End", KeyEvent.VK_END, resource = Res.drawable.arrow_right),
    )
)

@Composable
fun KeyButton(
    modifier: Modifier,
    model: KeyButtonModel,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(34.dp)
            .height(model.height.dp)
            .clickable { onClick() }
            .background(
                color = Colors.secondaryContainer,
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (model.resource != null) {
            Image(
                painter = painterResource(model.resource),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        } else {
            Text(
                text = model.title,
                color = Colors.textColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun Keyboard(callback: (keyCode: Int) -> Unit) {
    Column(
        modifier = Modifier.width(550.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        keyList.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                row.forEach { model ->
                    val modifier = if (model.weight != null) Modifier.weight(model.weight) else Modifier
                    KeyButton(
                        modifier = modifier,
                        model = model
                    ) { callback(model.keyCode) }
                }
            }
        }
    }
}