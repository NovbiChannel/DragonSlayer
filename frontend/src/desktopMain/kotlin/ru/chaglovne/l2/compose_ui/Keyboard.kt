package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dragonslayerfrontend.frontend.generated.resources.*
import keyboard.WindowsKeyCode
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.theme.Colors

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
        KeyButtonModel("esc", WindowsKeyCode.VC_ESCAPE, height = SMALL_BUTTON, weight = 1f),
        KeyButtonModel("f1", WindowsKeyCode.VC_F1, height = SMALL_BUTTON),
        KeyButtonModel("f2", WindowsKeyCode.VC_F2, height = SMALL_BUTTON),
        KeyButtonModel("f3", WindowsKeyCode.VC_F3, height = SMALL_BUTTON),
        KeyButtonModel("f4", WindowsKeyCode.VC_F4, height = SMALL_BUTTON),
        KeyButtonModel("f5", WindowsKeyCode.VC_F5, height = SMALL_BUTTON),
        KeyButtonModel("f6", WindowsKeyCode.VC_F6, height = SMALL_BUTTON),
        KeyButtonModel("f7", WindowsKeyCode.VC_F7, height = SMALL_BUTTON),
        KeyButtonModel("f8", WindowsKeyCode.VC_F8, height = SMALL_BUTTON),
        KeyButtonModel("f9", WindowsKeyCode.VC_F9, height = SMALL_BUTTON),
        KeyButtonModel("f10", WindowsKeyCode.VC_F10, height = SMALL_BUTTON),
        KeyButtonModel("f11", WindowsKeyCode.VC_F11, height = SMALL_BUTTON),
        KeyButtonModel("f12", WindowsKeyCode.VC_F12, height = SMALL_BUTTON),
        KeyButtonModel("del", WindowsKeyCode.VC_DELETE, height = SMALL_BUTTON),
    ),
    listOf(
        KeyButtonModel("`", WindowsKeyCode.VC_BACKQUOTE),
        KeyButtonModel("1", WindowsKeyCode.VC_1),
        KeyButtonModel("2", WindowsKeyCode.VC_2),
        KeyButtonModel("3", WindowsKeyCode.VC_3),
        KeyButtonModel("4", WindowsKeyCode.VC_4),
        KeyButtonModel("5", WindowsKeyCode.VC_5),
        KeyButtonModel("6", WindowsKeyCode.VC_6),
        KeyButtonModel("7", WindowsKeyCode.VC_7),
        KeyButtonModel("8", WindowsKeyCode.VC_8),
        KeyButtonModel("9", WindowsKeyCode.VC_9),
        KeyButtonModel("0", WindowsKeyCode.VC_0),
        KeyButtonModel("-", WindowsKeyCode.VC_MINUS),
        KeyButtonModel("=", WindowsKeyCode.VC_EQUALS),
        KeyButtonModel("backspace", WindowsKeyCode.VC_BACKSPACE, weight = 1f),
    ),
    listOf(
        KeyButtonModel("TAB", WindowsKeyCode.VC_TAB, weight = 1f),
        KeyButtonModel("Q", WindowsKeyCode.VC_Q),
        KeyButtonModel("W", WindowsKeyCode.VC_W),
        KeyButtonModel("E", WindowsKeyCode.VC_E),
        KeyButtonModel("R", WindowsKeyCode.VC_R),
        KeyButtonModel("T", WindowsKeyCode.VC_T),
        KeyButtonModel("Y", WindowsKeyCode.VC_Y),
        KeyButtonModel("U", WindowsKeyCode.VC_U),
        KeyButtonModel("I", WindowsKeyCode.VC_I),
        KeyButtonModel("O", WindowsKeyCode.VC_O),
        KeyButtonModel("P", WindowsKeyCode.VC_P),
        KeyButtonModel("[", WindowsKeyCode.VC_OPEN_BRACKET),
        KeyButtonModel("]", WindowsKeyCode.VC_CLOSE_BRACKET),
        KeyButtonModel("\\", WindowsKeyCode.VC_BACK_SLASH),
    ),
    listOf(
        KeyButtonModel("Caps Lock", WindowsKeyCode.VC_CAPS_LOCK, weight = 1f),
        KeyButtonModel("A", WindowsKeyCode.VC_A),
        KeyButtonModel("S", WindowsKeyCode.VC_S),
        KeyButtonModel("D", WindowsKeyCode.VC_D),
        KeyButtonModel("F", WindowsKeyCode.VC_F),
        KeyButtonModel("G", WindowsKeyCode.VC_G),
        KeyButtonModel("H", WindowsKeyCode.VC_H),
        KeyButtonModel("J", WindowsKeyCode.VC_J),
        KeyButtonModel("K", WindowsKeyCode.VC_K),
        KeyButtonModel("L", WindowsKeyCode.VC_L),
        KeyButtonModel(";", WindowsKeyCode.VC_SEMICOLON),
        KeyButtonModel("'", WindowsKeyCode.VC_QUOTE),
        KeyButtonModel("Enter", WindowsKeyCode.VC_ENTER, weight = 1f),
    ),
    listOf(
        KeyButtonModel("Shift", WindowsKeyCode.VC_SHIFT, weight = 1f),
        KeyButtonModel("Z", WindowsKeyCode.VC_Z),
        KeyButtonModel("X", WindowsKeyCode.VC_X),
        KeyButtonModel("C", WindowsKeyCode.VC_C),
        KeyButtonModel("V", WindowsKeyCode.VC_V),
        KeyButtonModel("B", WindowsKeyCode.VC_B),
        KeyButtonModel("N", WindowsKeyCode.VC_N),
        KeyButtonModel("M", WindowsKeyCode.VC_M),
        KeyButtonModel(",", WindowsKeyCode.VC_COMMA),
        KeyButtonModel(".", WindowsKeyCode.VC_PERIOD),
        KeyButtonModel("/", WindowsKeyCode.VC_SLASH),
        KeyButtonModel("Shift", WindowsKeyCode.VC_SHIFT, weight = 1f),
    ),
    listOf(
        KeyButtonModel("Ctrl", WindowsKeyCode.VC_CONTROL, weight = 1f),
        KeyButtonModel("Win", WindowsKeyCode.VC_HOME, resource = Res.drawable.mingcute_windows_fill),
        KeyButtonModel("Alt", WindowsKeyCode.VC_ALT),
        KeyButtonModel("Space", WindowsKeyCode.VC_SPACE, weight = 2f),
        KeyButtonModel("Alt", WindowsKeyCode.VC_ALT),
        KeyButtonModel("Ctrl", WindowsKeyCode.VC_CONTROL),
        KeyButtonModel("Left", WindowsKeyCode.VC_LEFT, resource = Res.drawable.arrow_left),
        KeyButtonModel("Up", WindowsKeyCode.VC_UP, resource = Res.drawable.arrow_up),
        KeyButtonModel("Down", WindowsKeyCode.VC_DOWN, resource = Res.drawable.arrow_down),
        KeyButtonModel("End", WindowsKeyCode.VC_END, resource = Res.drawable.arrow_right),
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

@NonRestartableComposable
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