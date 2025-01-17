package ru.chaglovne.l2.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.app_logo
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.theme.Colors

@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onMacroClick: () -> Unit,
    onEditorClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMacroStartStop: (isLaunched: Boolean) -> Unit
) {
    val macroKey = "MACRO"
    val editorKey = "EDITOR"
    val settingsKey = "SETTINGS"
    val profileKey = "PROFILE"

    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(macroKey) }
    var isActive by remember { mutableStateOf(false) }
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
            Image(
                painter = painterResource(Res.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(Modifier.height(13.dp))
            OutlinedText(
                text = "Dragon Slayer",
                fillColor = Colors.brandColor,
                outlineColor = Color.Black,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(35.dp))
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
            AccentButton("Profile", selectedTab == profileKey) {
                selectedTab = profileKey
                onProfileClick()
            }
            Spacer(Modifier.weight(1F))
            PlayStopButton(isActive) {
                isActive = !isActive
                onMacroStartStop(isActive)
                val message = if (isActive) "Программа запущена" else "Программа остановлена"
                scope.launch { snackbarHostState.showSnackbar(message) }
            }
            Spacer(Modifier.height(35.dp))
        }
    }
}