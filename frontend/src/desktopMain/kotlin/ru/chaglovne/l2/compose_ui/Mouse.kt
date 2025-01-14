package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.chaglovne.l2.theme.Colors
import java.awt.event.MouseEvent

@Composable
fun Mouse(callback: (keyCode: Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            PrimaryMouseButton(Modifier.weight(1f)) { callback(1) }
            PrimaryMouseButton(Modifier.weight(1f)) { callback(2) }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AdditionalMouseButton(Modifier.weight(1f)) { callback(5) }
                AdditionalMouseButton(Modifier.weight(1f)) { callback(4) }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Colors.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
fun AdditionalMouseButton(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .width(20.dp)
            .background(
                color = Colors.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick.invoke() }
    )
}

@Composable
fun PrimaryMouseButton(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(
                color = Colors.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick.invoke() }
    )
}