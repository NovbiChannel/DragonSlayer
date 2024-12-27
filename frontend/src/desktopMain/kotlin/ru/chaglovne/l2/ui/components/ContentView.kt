package ru.chaglovne.l2.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContentView(modifier: Modifier = Modifier, content: @Composable (modifier: Modifier) -> Unit) {
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content(Modifier.weight(1f))
        }
    }
}