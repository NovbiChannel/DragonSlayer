package ru.chaglovne.l2.components.counters.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dragonslayerfrontend.frontend.generated.resources.Res
import dragonslayerfrontend.frontend.generated.resources.arrow_down
import dragonslayerfrontend.frontend.generated.resources.arrow_up
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.counters.ui_logic.CounterComponent
import ru.chaglovne.l2.theme.Colors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CounterUI(component: CounterComponent, modifier: Modifier, output: (Int) -> Unit ) {
    val model by component.model.subscribeAsState()
    var isError by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val borderColor = when {
        isFocused && isError -> Colors.brandColor
        isFocused -> Colors.accentColor
        isError -> Colors.brandColor
        else -> Color.Transparent
    }

    LaunchedEffect(model.count) {
        output(model.count)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = Colors.onAccentColor
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = model.count.mapValue(),
            onValueChange = { change ->
                isError = try {
                    component.setTextValue(change)
                    false
                } catch (e: Exception) {
                    e.printStackTrace()
                    true
                }
            },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
            ,
            singleLine = true,
            interactionSource = interactionSource,
            textStyle = TextStyle(color = Colors.textColor, fontSize = 14.sp),
            cursorBrush = SolidColor(Colors.textColor)
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = model.count.toString(),
                innerTextField = innerTextField,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                isError = isError,
                enabled = true,
                placeholder = {
                    Text(
                        text = "0 sec",
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
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            CountButton(
                resource = Res.drawable.arrow_up,
                isActive = model.isCanIncrement
            ) {
                component.increment()
            }
            CountButton(
                resource = Res.drawable.arrow_down,
                isActive = model.isCanDecrement
            ) {
                component.decrement()
            }
        }
    }
}

private fun Int.mapValue(): String = if (this == 0) "" else this.toString()

@Composable
private fun CountButton(resource: DrawableResource, isActive: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(34.dp)
            .clickable { if (isActive) onClick() }
            .background(color = if (isActive) Colors.accentColor else Colors.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(resource),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = Colors.textColor
        )
    }
}