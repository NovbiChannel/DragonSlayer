package ru.chaglovne.l2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentView(modifier: Modifier = Modifier, content: @Composable (modifier: Modifier) -> Unit) {
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content(Modifier.weight(1f))
            Spacer(Modifier.height(16.dp))
            InfoPanel()
        }
    }
}