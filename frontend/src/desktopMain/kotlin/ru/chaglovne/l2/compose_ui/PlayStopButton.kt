package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.play_icon
import l2macros.frontend.generated.resources.stop_icon
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.theme.Colors

@Composable
fun PlayStopButton(
    isActive: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isActive) Colors.accentColor else Colors.onAccentColor
    val iconResource = if (isActive) Res.drawable.stop_icon else Res.drawable.play_icon
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Colors.textColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}