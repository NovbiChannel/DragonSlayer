package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.battle_icon
import l2macros.frontend.generated.resources.settings_icon
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.theme.Colors

@Composable
fun MacroItem(
    title: String,
    keyTitle: String
) {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = Colors.onAccentColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.battle_icon),
                contentDescription = null,
                modifier = Modifier.size(34.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = title,
                color = Colors.textColor,
                fontSize = 16.sp
            )
            Spacer(Modifier.weight(1F))
            Column(
                modifier = Modifier.height(34.dp).wrapContentWidth()
            ) {
                Spacer(Modifier.weight(1F))
                Text(
                    text = "press $keyTitle for start",
                    color = Colors.secondaryTextColor,
                    fontSize = 9.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                val fontSize = if (keyTitle.count() <= 2) 16.sp else 12.sp
                Text(
                    text = keyTitle,
                    color = Colors.textColor,
                    fontSize = fontSize,
                )
            }
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {
                    println("Open Settings")
                }
            ) {
                Image(
                    painter = painterResource(Res.drawable.settings_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}