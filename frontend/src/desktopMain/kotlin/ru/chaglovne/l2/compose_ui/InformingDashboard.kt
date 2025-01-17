package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.character
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.theme.Colors

@Composable
fun InformingDashboard(
    modifier: Modifier,
    imageSize: Dp,
    fontSize: TextUnit,
    text: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.character),
                contentDescription = null,
                modifier = Modifier.size(imageSize)
            )
            Text(
                text = text,
                color = Colors.textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}