package ru.chaglovne.l2.components.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.ic_baseline_close
import ru.chaglovne.l2.components.dialog.ui_logic.DialogComponent
import ru.chaglovne.l2.components.editor.ui.IconButton
import ru.chaglovne.l2.theme.Colors

@Composable
fun DialogUI(
    component: DialogComponent,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { component.onDismissClicked() },
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 200.dp)
                .background(
                    color = Colors.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
            ,
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row {
                    Text(
                        text = component.title,
                        color = Colors.textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(Res.drawable.ic_baseline_close) {
                        component.onDismissClicked()
                    }
                }
                component.message?.let {
                    Text(
                        text = it,
                        color = Colors.textColor,
                        fontSize = 16.sp
                    )
                }
                content()
            }
        }
    }
}