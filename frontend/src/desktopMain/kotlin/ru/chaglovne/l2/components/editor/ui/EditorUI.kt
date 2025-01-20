package ru.chaglovne.l2.components.editor.ui

import LoopType
import TimeUnit
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import l2macros.frontend.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.counters.ui.CounterUI
import ru.chaglovne.l2.components.counters.ui_logic.DefaultCounterComponent
import ru.chaglovne.l2.components.dialog.ui.DialogUI
import ru.chaglovne.l2.components.dialog.ui_logic.DefaultDialogComponent
import ru.chaglovne.l2.components.editor.ui_logic.EditorComponent
import ru.chaglovne.l2.compose_ui.AccentButton
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
                component.onAddEvent(EditorComponent.EventType.KeyPress(keyCode))
                component.onAddEvent(EditorComponent.EventType.KeyRelease(keyCode))
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
                            contentDescription = null
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
                    component.onAddEvent(EditorComponent.EventType.MousePress(keyCode))
                    component.onAddEvent(EditorComponent.EventType.MouseRelease(keyCode))
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
    event: EditorComponent.Event,
    outputHandler: (EditorComponent.Output) -> Unit,
    selectedItemId: Int
) {
    val isSelected = event.id == selectedItemId
    val bgColor = if (isSelected) Colors.accentColor else Colors.onAccentColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(
                color = bgColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { outputHandler(EditorComponent.Output.Select(event.id)) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val resource = when(event.eventType) {
                is EditorComponent.EventType.Delay -> Res.drawable.access_time
                is EditorComponent.EventType.KeyPress, is EditorComponent.EventType.KeyRelease -> Res.drawable.enter_key
                is EditorComponent.EventType.MousePress, is EditorComponent.EventType.MouseRelease -> Res.drawable.solar_mouse_linear
            }

            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(resource),
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = event.title,
                color = Colors.textColor,
                fontSize = 14.sp
            )

            Spacer(Modifier.weight(1f))

            if (event.id == selectedItemId) {
                ItemInteractions(
                    event = event,
                    isDelayEvent = event.eventType is EditorComponent.EventType.Delay,
                    outputHandler = outputHandler
                )
            }
        }
    }
}

@Composable
fun ItemInteractions(
    event: EditorComponent.Event,
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
        outputHandler(EditorComponent.Output.MoveUp(event.id))
    }
    IconButton(Res.drawable.arrow_down) {
        outputHandler(EditorComponent.Output.MoveDown(event.id))
    }
    IconButton(Res.drawable.mdi_delete_outline) {
        outputHandler(EditorComponent.Output.Delete(event.id))
    }

    if (isDelayEvent && isShowDialog) {
        DelayEditorDialog(event, outputHandler) { isShowDialog = false }
    }
}

@Composable
private fun DelayEditorDialog(event: EditorComponent.Event, outputHandler: (EditorComponent.Output) -> Unit, onDismissed: () -> Unit) {
    var isShowDropMenu by remember { mutableStateOf(false) }
    val type = event.eventType as EditorComponent.EventType.Delay
    var timeUnit by remember { mutableStateOf(type.timeUnit) }

    DialogUI(
        DefaultDialogComponent(title = "Изменить время ожидания", onDismissed = onDismissed)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CounterUI(
                component = DefaultCounterComponent(timeUnit.value),
                modifier = Modifier.weight(1f)
            ) { count -> timeUnit.value = count
                println(timeUnit.value)
            }
            Row(
                modifier = Modifier
                    .width(100.dp)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { isShowDropMenu = !isShowDropMenu }
                    .background(Colors.onAccentColor),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(16.dp))
                Text(
                    text = timeUnit.getName(),
                    color = Colors.textColor,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(Res.drawable.arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                    tint = Colors.textColor
                )
                if (isShowDropMenu) {
                    DropTimeUnitMenu(
                        delay = timeUnit.value,
                        callback = { selectedTimeUnit -> timeUnit = selectedTimeUnit},
                        dismissed = { isShowDropMenu = false }
                    )
                }
                Spacer(Modifier.width(16.dp))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Spacer(Modifier.weight(1f))
            AccentButton(
                title = "Отменить",
                isSelected = false,
                onClick = onDismissed
            )
            AccentButton(
                title = "Сохранить",
                isSelected = true
            ) {
                onDismissed()
                outputHandler(EditorComponent.Output.SetDelay(event.id, timeUnit))
            }
        }
    }
}

@Composable
private fun DropTimeUnitMenu(delay: Int, callback: (TimeUnit) -> Unit, dismissed: () -> Unit) {
    var dropDownExpand by remember { mutableStateOf(true) }
    DropdownMenu(
        expanded = dropDownExpand,
        onDismissRequest = {
            dropDownExpand = false
            dismissed()
        },
        modifier = Modifier
            .background(
                color = Colors.background
            )
    ) {
        val timeUnits = listOf(TimeUnit.Millisecond(delay), TimeUnit.Seconds(delay), TimeUnit.Minutes(delay))
        timeUnits.forEach { TimeUnitMenuItem(it, callback) { dropDownExpand = false } }
    }
}

@Composable
private fun TimeUnitMenuItem(item: TimeUnit, callback: (TimeUnit) -> Unit, dismissed: () -> Unit) {
    DropdownMenuItem(
        onClick = {
            callback(item)
            dismissed()
        }
    ) {
        Text(
            text = item.getName(),
            color = Colors.textColor,
            fontSize = 12.sp
        )
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