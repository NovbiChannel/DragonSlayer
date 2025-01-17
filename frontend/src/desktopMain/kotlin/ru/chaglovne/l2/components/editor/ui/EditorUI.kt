package ru.chaglovne.l2.components.editor.ui

import LoopType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import l2macros.frontend.generated.resources.*
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.access_time
import l2macros.frontend.generated.resources.foundation_arrow_right
import l2macros.frontend.generated.resources.repeat
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.dialog.ui.DialogUI
import ru.chaglovne.l2.components.dialog.ui_logic.DefaultDialogComponent
import ru.chaglovne.l2.components.editor.ui_logic.EditorComponent
import ru.chaglovne.l2.compose_ui.InformingDashboard
import ru.chaglovne.l2.compose_ui.Keyboard
import ru.chaglovne.l2.compose_ui.Mouse
import ru.chaglovne.l2.theme.Colors

@Composable
fun EditorContent(component: EditorComponent) {
    val model by component.model.subscribeAsState()
    var dropDownExpand by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EventList(
            modifier = Modifier.weight(1f),
            items = model.events,
            selectedItemId = model.selectedEventId,
            outputHandler = component::outputHandler
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Keyboard { keyCode ->
                component.onAddEvent(EditorComponent.EventType.KeyboardClick(keyCode))
            }
            Column(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = {
                            component.onAddEvent(EditorComponent.EventType.Delay())
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Colors.onAccentColor,
                            contentColor = Colors.textColor
                        ),
                        modifier = Modifier.height(34.dp).weight(1f)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.access_time),
                            contentDescription = "add delay"
                        )
                    }
                    Button(
                        onClick = {
                            dropDownExpand = !dropDownExpand
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Colors.onAccentColor,
                            contentColor = Colors.textColor
                        ),
                        modifier = Modifier.height(34.dp).weight(1f)
                    ) {
                        val repetitions = (model.loopType as? LoopType.CUSTOM)?.repetitions
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            val resource = when (model.loopType) {
                                is LoopType.CUSTOM -> Res.drawable.ri_repeat_2_line
                                LoopType.INFINITE -> Res.drawable.ri_repeat_2_line
                                LoopType.SINGLE -> Res.drawable.foundation_arrow_right
                            }
                            Image(
                                painter = painterResource(resource),
                                contentDescription = "loop type"
                            )
                            if (repetitions != null) {
                                Text(
                                    text = repetitions.toString(),
                                    color = Colors.textColor,
                                    fontSize = 9.sp,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = dropDownExpand,
                            onDismissRequest = { dropDownExpand = false },
                            modifier = Modifier
                                .background(
                                    color = Colors.background
                                )
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    if (dropDownExpand) {
                                        dropDownExpand = false
                                        component.setLoopType(LoopType.SINGLE)
                                    }
                                }
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.foundation_arrow_right),
                                    contentDescription = null
                                )
                                Text(text = "Single", color = Colors.textColor)
                            }
                            DropdownMenuItem(
                                onClick = {
                                    if (dropDownExpand) {
                                        dropDownExpand = false
                                        component.setLoopType(LoopType.INFINITE)
                                    }
                                }
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.repeat),
                                    contentDescription = null
                                )
                                Text(text = "Repeatable", color = Colors.textColor)
                            }
                        }
                    }
                }
                Mouse { keyCode ->
                    component.onAddEvent(EditorComponent.EventType.MouseClick(keyCode))
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
            ) {
                Text("test")
            }
            Button(
                onClick = {},
            ) {
                Text("clean")
            }
            Button(
                onClick = {},
            ) {
                Text("save")
            }
        }
    }
}

@Composable
fun EventList(
    modifier: Modifier,
    items: List<EditorComponent.Event>,
    outputHandler: (EditorComponent.Output) -> Unit,
    selectedItemId: Int
) {
    Box(modifier) {
        val listState = rememberLazyListState()

        if (items.isEmpty()) {
            InformingDashboard(
                modifier = Modifier.fillMaxSize(),
                imageSize = 100.dp,
                fontSize = 14.sp,
                text = "Нажимай на кнопки\n" +
                        "виртуальной клавиатуры\n" +
                        "что-бы записать последовательность нажатий.\n" +
                        "Не забудь дать макросу наименование."
            )
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items) { item ->
                    EventItem(item, outputHandler, selectedItemId)
                }
            }
        }
    }
}

@Composable
fun EventItem(
    item: EditorComponent.Event,
    outputHandler: (EditorComponent.Output) -> Unit,
    selectedItemId: Int
) {

    val isSelected = item.id == selectedItemId
    val bgColor = if (isSelected) Colors.accentColor else Colors.onAccentColor
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(
                color = bgColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { outputHandler(EditorComponent.Output.Select(item.id)) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val resource = when(item.eventType) {
                is EditorComponent.EventType.Delay -> Res.drawable.access_time
                is EditorComponent.EventType.KeyboardClick -> Res.drawable.enter_key
                is EditorComponent.EventType.MouseClick -> Res.drawable.solar_mouse_linear
            }
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(resource),
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = item.title,
                color = Colors.textColor,
                fontSize = 14.sp
            )

            Spacer(Modifier.weight(1f))

            if (item.id == selectedItemId) {
                ItemInteractions(
                    eventId = item.id,
                    isDelayEvent = item.eventType is EditorComponent.EventType.Delay,
                    outputHandler = outputHandler
                )
            }
        }
    }
}

@Composable
fun ItemInteractions(
    eventId: Int,
    isDelayEvent: Boolean,
    outputHandler: (EditorComponent.Output) -> Unit
) {
    var isShowDialog by remember { mutableStateOf(false) }
    if (isDelayEvent) {
        IconButton(Res.drawable.edit_outline) {
            isShowDialog = true
        }
    }
    IconButton(Res.drawable.arrow_up) {
        outputHandler(EditorComponent.Output.MoveUp(eventId))
    }
    IconButton(Res.drawable.arrow_down) {
        outputHandler(EditorComponent.Output.MoveDown(eventId))
    }
    IconButton(Res.drawable.mdi_delete_outline) {
        outputHandler(EditorComponent.Output.Delete(eventId))
    }

    if (isShowDialog) {
        DialogUI(
            DefaultDialogComponent(title = "Изменить время ожидания", onDismissed = { isShowDialog = false })
        ) {

        }
    }
}

@Composable
fun IconButton(
    resource: DrawableResource,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clip(CircleShape)
        .size(32.dp)
        .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(18.dp),
            painter = painterResource(resource),
            contentDescription = null
        )
    }
}