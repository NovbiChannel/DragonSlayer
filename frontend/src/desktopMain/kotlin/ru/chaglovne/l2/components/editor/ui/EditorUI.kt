package ru.chaglovne.l2.components.editor.ui

import EventType
import InputType
import LoopType
import MouseKeyCodes
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
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import l2macros.frontend.generated.resources.*
import onInputListener
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.counters.ui.CounterUI
import ru.chaglovne.l2.components.counters.ui_logic.DefaultCounterComponent
import ru.chaglovne.l2.components.dialog.ui.DialogUI
import ru.chaglovne.l2.components.dialog.ui_logic.DefaultDialogComponent
import ru.chaglovne.l2.components.editor.ui_logic.EditorComponent
import ru.chaglovne.l2.components.input.ui.TextInputUI
import ru.chaglovne.l2.compose_ui.*
import ru.chaglovne.l2.theme.Colors

@Composable
fun EditorContent(component: EditorComponent, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    val model by component.model.subscribeAsState()
    var dropDownExpand by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowSaveDialog by remember { mutableStateOf(false) }

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
                component.onAddEvent(EventType.KeyPress(keyCode))
                component.onAddEvent(EventType.KeyRelease(keyCode))
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
                            component.onAddEvent(EventType.Delay())
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
                    component.onAddEvent(EventType.MousePress(keyCode))
                    component.onAddEvent(EventType.MouseRelease(keyCode))
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextInputUI(
                component = component.textInputComponent,
                modifier = Modifier.weight(1f).height(36.dp)
            )
            AccentButton(
                title = "Очистить",
                isSelected = false
            ) { isShowDialog = true }
            AccentButton(
                title = "Сохранить",
                isSelected = model.events.isNotEmpty()
            ) {
                if (model.title.isBlank()) {
                    component.textInputComponent.onError()
                    scope.launch { snackbarHostState.showSnackbar("Введите наименование макроса") }
                } else { isShowSaveDialog = true }
            }
        }
    }

    if (isShowDialog) {
        ConfirmationDialog(
            title = "Подтверждение",
            message = "Ты действительно хочешь очистить редактор макроса?",
            onDismissed = { isShowDialog = false }
        ) {
            isShowDialog = false
            component.outputHandler(EditorComponent.Output.Clear)
        }
    }

    if (isShowSaveDialog) {
        SaveMacroDialog(onDismissed = { isShowSaveDialog = false }) { type ->
            isShowSaveDialog = false
            component.outputHandler(EditorComponent.Output.SaveData(type))
            scope.launch {
                snackbarHostState.showSnackbar("Макрос успешно сохранён")
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
                is EventType.Delay -> Res.drawable.access_time
                is EventType.KeyPress, is EventType.KeyRelease -> Res.drawable.enter_key
                is EventType.MousePress, is EventType.MouseRelease -> Res.drawable.solar_mouse_linear
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
                val isReleaseEvent = event.eventType is EventType.KeyRelease || event.eventType is EventType.MouseRelease
                ItemInteractions(
                    event = event,
                    isReleaseEvent = isReleaseEvent,
                    outputHandler = outputHandler
                )
            }
        }
    }
}

@Composable
fun ItemInteractions(
    event: EditorComponent.Event,
    isReleaseEvent: Boolean,
    outputHandler: (EditorComponent.Output) -> Unit
) {
    var isShowDialog by remember { mutableStateOf(false) }

    if (!isReleaseEvent) IconButton(Res.drawable.edit_outline) { isShowDialog = true }
    IconButton(Res.drawable.arrow_up) { outputHandler(EditorComponent.Output.MoveUp(event.id)) }
    IconButton(Res.drawable.arrow_down) { outputHandler(EditorComponent.Output.MoveDown(event.id)) }
    IconButton(Res.drawable.mdi_delete_outline) { outputHandler(EditorComponent.Output.Delete(event.id)) }

    when (event.eventType) {
        is EventType.Delay -> {
            if (isShowDialog) DelayEditorDialog(event, outputHandler) { isShowDialog = false }
        }
        is EventType.KeyPress, is EventType.MousePress-> {
            if (isShowDialog) KeyPressIntervalEditorDialog(event, outputHandler) { isShowDialog = false }
        }
        else -> {}
    }
}

private fun getInitialTimeUnit(type: EventType): TimeUnit {
    return when (type) {
        is EventType.KeyPress -> type.timeUnit ?: TimeUnit.Millisecond(0)
        is EventType.MousePress -> type.timeUnit ?: TimeUnit.Millisecond(0)
        is EventType.Delay -> type.timeUnit
        else -> TimeUnit.Millisecond(TimeUnit.DEFAULT_VALUE)
    }
}

@Composable
private fun KeyPressIntervalEditorDialog(event: EditorComponent.Event, outputHandler: (EditorComponent.Output) -> Unit, onDismissed: () -> Unit) {
    TimeUnitEditorDialog(
        title = "Изменить интервал нажатий",
        event = event,
        outputHandler = outputHandler,
        onDismissed = onDismissed
    )
}

@Composable
private fun DelayEditorDialog(event: EditorComponent.Event, outputHandler: (EditorComponent.Output) -> Unit, onDismissed: () -> Unit) {
    TimeUnitEditorDialog(
        title = "Изменить время ожидания",
        event = event,
        outputHandler = outputHandler,
        onDismissed = onDismissed
    )
}

@Composable
private fun TimeUnitEditorDialog(
    title: String,
    event: EditorComponent.Event,
    outputHandler: (EditorComponent.Output) -> Unit,
    onDismissed: () -> Unit
) {
    var isShowDropMenu by remember { mutableStateOf(false) }
    var timeUnit by remember { mutableStateOf(getInitialTimeUnit(event.eventType)) }

    DialogUI(
        DefaultDialogComponent(title = title, onDismissed = onDismissed)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CounterUI(
                component = DefaultCounterComponent(timeUnit.value),
                modifier = Modifier.weight(1f)
            ) { count ->
                timeUnit.value = count
                println(timeUnit.value)
            }
            Box {
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
                        modifier = Modifier.weight(1f).padding(0.dp)
                    )
                    Icon(
                        painter = painterResource(Res.drawable.arrow_down),
                        contentDescription = null,
                        modifier = Modifier.size(10.dp),
                        tint = Colors.textColor
                    )
                    Spacer(Modifier.width(16.dp))
                }
                if (isShowDropMenu) {
                    DropTimeUnitMenu(
                        delay = timeUnit.value,
                        callback = { selectedTimeUnit -> timeUnit = selectedTimeUnit },
                        dismissed = { isShowDropMenu = false }
                    )
                }
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

                when (event.eventType) {
                    is EventType.Delay -> {
                        outputHandler(EditorComponent.Output.SetDelay(event.id, timeUnit))
                    }
                    is EventType.KeyPress, is EventType.MousePress -> {
                        outputHandler(EditorComponent.Output.SetInterval(event.id, timeUnit))
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun ConfirmationDialog(title: String, message: String, onDismissed: () -> Unit, onSuccess: () -> Unit) {
    DialogUI(
        DefaultDialogComponent(title = title, onDismissed = onDismissed)
    ) {
        Text(
            text = message,
            color = Colors.textColor,
            fontSize = 14.sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Spacer(Modifier.weight(1f))
            AccentButton(
                title = "Нет",
                isSelected = false,
                onClick = onDismissed
            )
            AccentButton(
                title = "Очистить",
                isSelected = true,
                onClick = onSuccess
            )
        }
    }
}

@Composable
private fun SaveMacroDialog(onDismissed: () -> Unit, onSuccess: (InputType) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val listenerJob = onInputListener(coroutineScope) { inputType -> onSuccess(inputType) }

        onDispose {
            listenerJob.cancel()
            GlobalScreen.unregisterNativeHook()
        }
    }

    DialogUI(
        DefaultDialogComponent(
            title = "Почти все готово!",
            onDismissed = onDismissed
        )
    ) {
        Text(
            text = "Теперь вам нужно нажать на клавишу или кнопку мыши,\nкоторая будет использоваться для запуска\nи остановки вашего макроса.",
            color = Colors.textColor,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun DropTimeUnitMenu(delay: Int, callback: (TimeUnit) -> Unit, dismissed: () -> Unit) {
    var dropDownExpand by remember { mutableStateOf(true) }

    androidx.compose.material3.DropdownMenu(
        expanded = dropDownExpand,
        onDismissRequest = {
            dropDownExpand = false
            dismissed()
        },
        containerColor = Colors.background,
        shape = RoundedCornerShape(8.dp)
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