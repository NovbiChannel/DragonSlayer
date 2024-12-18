package ru.chaglovne.l2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InfoPanel() {
    val appVersion = "1.0.0"
    val warning = "None"
    val status = "Stopped"
    val mousePosition = Pair(0, 0)

    Box(Modifier.wrapContentHeight().fillMaxWidth()) {
        Text(
            text = "Warning: $warning",
            modifier = Modifier.align(Alignment.TopStart)
        )
        Text(
            text = "Version: $appVersion",
            modifier = Modifier.align(Alignment.BottomStart)
        )
        Text(
            text = "Status: $status",
            modifier = Modifier.align(Alignment.TopEnd).padding(bottom = 8.dp)
        )
        Text(
            text = "Mouse Position: (${mousePosition.first}, ${mousePosition.second})",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}