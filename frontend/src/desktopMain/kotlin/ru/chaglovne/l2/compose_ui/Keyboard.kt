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
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
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
        KeyButtonModel("esc", NativeKeyEvent.VC_ESCAPE, height = SMALL_BUTTON, weight = 1f),
        KeyButtonModel("f1", NativeKeyEvent.VC_F1, height = SMALL_BUTTON),
        KeyButtonModel("f2", NativeKeyEvent.VC_F2, height = SMALL_BUTTON),
        KeyButtonModel("f3", NativeKeyEvent.VC_F3, height = SMALL_BUTTON),
        KeyButtonModel("f4", NativeKeyEvent.VC_F4, height = SMALL_BUTTON),
        KeyButtonModel("f5", NativeKeyEvent.VC_F5, height = SMALL_BUTTON),
        KeyButtonModel("f6", NativeKeyEvent.VC_F6, height = SMALL_BUTTON),
        KeyButtonModel("f7", NativeKeyEvent.VC_F7, height = SMALL_BUTTON),
        KeyButtonModel("f8", NativeKeyEvent.VC_F8, height = SMALL_BUTTON),
        KeyButtonModel("f9", NativeKeyEvent.VC_F9, height = SMALL_BUTTON),
        KeyButtonModel("f10", NativeKeyEvent.VC_F10, height = SMALL_BUTTON),
        KeyButtonModel("f11", NativeKeyEvent.VC_F11, height = SMALL_BUTTON),
        KeyButtonModel("f12", NativeKeyEvent.VC_F12, height = SMALL_BUTTON),
        KeyButtonModel("del", NativeKeyEvent.VC_DELETE, height = SMALL_BUTTON),
    ),
    listOf(
        KeyButtonModel("`", NativeKeyEvent.VC_BACKQUOTE),
        KeyButtonModel("1", NativeKeyEvent.VC_1),
        KeyButtonModel("2", NativeKeyEvent.VC_2),
        KeyButtonModel("3", NativeKeyEvent.VC_3),
        KeyButtonModel("4", NativeKeyEvent.VC_4),
        KeyButtonModel("5", NativeKeyEvent.VC_5),
        KeyButtonModel("6", NativeKeyEvent.VC_6),
        KeyButtonModel("7", NativeKeyEvent.VC_7),
        KeyButtonModel("8", NativeKeyEvent.VC_8),
        KeyButtonModel("9", NativeKeyEvent.VC_9),
        KeyButtonModel("0", NativeKeyEvent.VC_0),
        KeyButtonModel("-", NativeKeyEvent.VC_MINUS),
        KeyButtonModel("=", NativeKeyEvent.VC_EQUALS),
        KeyButtonModel("backspace", NativeKeyEvent.VC_BACKSPACE, weight = 1f),
    ),
    listOf(
        KeyButtonModel("TAB", NativeKeyEvent.VC_TAB, weight = 1f),
        KeyButtonModel("Q", NativeKeyEvent.VC_Q),
        KeyButtonModel("W", NativeKeyEvent.VC_W),
        KeyButtonModel("E", NativeKeyEvent.VC_E),
        KeyButtonModel("R", NativeKeyEvent.VC_R),
        KeyButtonModel("T", NativeKeyEvent.VC_T),
        KeyButtonModel("Y", NativeKeyEvent.VC_Y),
        KeyButtonModel("U", NativeKeyEvent.VC_U),
        KeyButtonModel("I", NativeKeyEvent.VC_I),
        KeyButtonModel("O", NativeKeyEvent.VC_O),
        KeyButtonModel("P", NativeKeyEvent.VC_P),
        KeyButtonModel("[", NativeKeyEvent.VC_OPEN_BRACKET),
        KeyButtonModel("]", NativeKeyEvent.VC_CLOSE_BRACKET),
        KeyButtonModel("\\", NativeKeyEvent.VC_BACK_SLASH),
    ),
    listOf(
        KeyButtonModel("Caps Lock", NativeKeyEvent.VC_CAPS_LOCK, weight = 1f),
        KeyButtonModel("A", NativeKeyEvent.VC_A),
        KeyButtonModel("S", NativeKeyEvent.VC_S),
        KeyButtonModel("D", NativeKeyEvent.VC_D),
        KeyButtonModel("F", NativeKeyEvent.VC_F),
        KeyButtonModel("G", NativeKeyEvent.VC_G),
        KeyButtonModel("H", NativeKeyEvent.VC_H),
        KeyButtonModel("J", NativeKeyEvent.VC_J),
        KeyButtonModel("K", NativeKeyEvent.VC_K),
        KeyButtonModel("L", NativeKeyEvent.VC_L),
        KeyButtonModel(";", NativeKeyEvent.VC_SEMICOLON),
        KeyButtonModel("'", NativeKeyEvent.VC_QUOTE),
        KeyButtonModel("Enter", NativeKeyEvent.VC_ENTER, weight = 1f),
    ),
    listOf(
        KeyButtonModel("Shift", NativeKeyEvent.VC_SHIFT, weight = 1f),
        KeyButtonModel("Z", NativeKeyEvent.VC_Z),
        KeyButtonModel("X", NativeKeyEvent.VC_X),
        KeyButtonModel("C", NativeKeyEvent.VC_C),
        KeyButtonModel("V", NativeKeyEvent.VC_V),
        KeyButtonModel("B", NativeKeyEvent.VC_B),
        KeyButtonModel("N", NativeKeyEvent.VC_N),
        KeyButtonModel("M", NativeKeyEvent.VC_M),
        KeyButtonModel(",", NativeKeyEvent.VC_COMMA),
        KeyButtonModel(".", NativeKeyEvent.VC_PERIOD),
        KeyButtonModel("/", NativeKeyEvent.VC_SLASH),
        KeyButtonModel("Shift", NativeKeyEvent.VC_SHIFT, weight = 1f),
    ),
    listOf(
        KeyButtonModel("Ctrl", NativeKeyEvent.VC_CONTROL, weight = 1f),
        KeyButtonModel("Win", NativeKeyEvent.VC_HOME, resource = Res.drawable.mingcute_windows_fill),
        KeyButtonModel("Alt", NativeKeyEvent.VC_ALT),
        KeyButtonModel("Space", NativeKeyEvent.VC_SPACE, weight = 2f),
        KeyButtonModel("Alt", NativeKeyEvent.VC_ALT),
        KeyButtonModel("Ctrl", NativeKeyEvent.VC_CONTROL),
        KeyButtonModel("Left", NativeKeyEvent.VC_LEFT, resource = Res.drawable.arrow_left),
        KeyButtonModel("Up", NativeKeyEvent.VC_UP, resource = Res.drawable.arrow_up),
        KeyButtonModel("Down", NativeKeyEvent.VC_DOWN, resource = Res.drawable.arrow_down),
        KeyButtonModel("End", NativeKeyEvent.VC_END, resource = Res.drawable.arrow_right),
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