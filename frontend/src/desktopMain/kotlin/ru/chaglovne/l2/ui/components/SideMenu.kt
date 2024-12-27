package ru.chaglovne.l2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.chaglovne.l2.theme.Colors

@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    onMacroClick: () -> Unit,
    onEditorClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val macroKey = "MACRO"
    val editorKey = "EDITOR"
    val settingsKey = "SETTINGS"
    var selectedTab by remember { mutableStateOf(macroKey) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Colors.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(13.dp))
            OutlinedText(
                text = "Dragon Slayer",
                fillColor = Colors.brandColor,
                outlineColor = Color.Black,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(63.dp))
            AccentButton("Macros", selectedTab == macroKey) {
                selectedTab = macroKey
                onMacroClick()
            }
            AccentButton("Editor", selectedTab == editorKey) {
                selectedTab = editorKey
                onEditorClick()
            }
            AccentButton("Settings", selectedTab == settingsKey) {
                selectedTab = settingsKey
                onSettingsClick()
            }
        }
    }
}