package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.chaglovne.l2.theme.Colors

@Composable
fun AccentButton(title: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Colors.accentColor else Colors.onAccentColor
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Colors.textColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = title,
            color = Colors.textColor,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}