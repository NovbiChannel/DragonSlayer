package ru.chaglovne.l2.components.input.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.chaglovne.l2.components.input.ui_logic.TextInputComponent
import ru.chaglovne.l2.theme.Colors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextInputUI(component: TextInputComponent, modifier: Modifier = Modifier) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val model by component.model.subscribeAsState()

    BasicTextField(
        value = model.input,
        onValueChange = { change -> component.onInputChanged(change) },
        modifier = modifier
            .border(
                width = 2.dp,
                color = when {
                    model.isError -> Colors.brandColor
                    isFocused -> Colors.accentColor
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(8.dp)
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        textStyle = TextStyle(color = Colors.textColor, fontSize = 14.sp),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Colors.textColor),
        singleLine = true,
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            value = model.input,
            innerTextField = innerTextField,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            isError = model.isError,
            enabled = true,
            placeholder = {
                Text(
                    text = model.placeholder,
                    fontSize = 14.sp,
                    color = Colors.secondaryTextColor
                )
            },
            interactionSource = interactionSource,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Colors.textColor,
                backgroundColor = Colors.onAccentColor
            ),
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
        )
    }
}