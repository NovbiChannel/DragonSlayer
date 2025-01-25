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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.app_logo
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.root.ui_logic.RootComponent
import ru.chaglovne.l2.theme.Colors

@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    outputHandler: (output: RootComponent.Output) -> Unit,
    model: RootComponent.Model
) {
    val scope = rememberCoroutineScope()
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
            AccentButton(
                title = "Макросы",
                isSelected = model.isMacroSelected,
                modifier = Modifier.fillMaxWidth()
            ) { outputHandler(RootComponent.Output.MacroClick) }
            AccentButton(
                title = "Редактор",
                isSelected = model.isEditorSelected,
                modifier = Modifier.fillMaxWidth()
            ) { outputHandler(RootComponent.Output.EditorClick) }
            AccentButton(
                title = "Профиль",
                isSelected = model.isProfileSelected,
                modifier = Modifier.fillMaxWidth()
            ) { outputHandler(RootComponent.Output.ProfileClick) }
            Spacer(Modifier.weight(1F))
            PlayStopButton(isActive) {
                isActive = !isActive
                outputHandler(RootComponent.Output.MacroStartStop(isActive, scope))
                val message = if (isActive) "Программа запущена" else "Программа остановлена"
                scope.launch { snackbarHostState.showSnackbar(message) }
            }
            Spacer(Modifier.height(35.dp))
        }
    }
}