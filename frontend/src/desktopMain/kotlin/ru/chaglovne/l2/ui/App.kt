package ru.chaglovne.l2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.chaglovne.l2.theme.Colors
import ru.chaglovne.l2.ui.components.ContentView
import ru.chaglovne.l2.ui.components.SideMenu

@Composable
fun App() {
    Row(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
            .padding(16.dp)
    ) {
        SideMenu(Modifier.weight(1f))
        Spacer(Modifier.width(16.dp))
        ContentView(Modifier.weight(4f)) { modifier ->
            Box(modifier
                .fillMaxSize()
                .background(
                color = Colors.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            ))
        }
    }
}