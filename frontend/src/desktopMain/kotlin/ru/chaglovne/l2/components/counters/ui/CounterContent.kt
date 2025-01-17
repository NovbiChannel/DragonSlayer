package ru.chaglovne.l2.components.counters.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.arrow_down
import l2macros.frontend.generated.resources.arrow_up
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.counters.ui_logic.CounterComponent
import ru.chaglovne.l2.theme.Colors

@Composable
fun CounterUI(component: CounterComponent, modifier: Modifier, output: (Long) -> Unit ) {
    val model by component.model.subscribeAsState()
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = Colors.onAccentColor
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))
        Text(
            text = model.count.toString(),
            color = Colors.textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.weight(1f))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            CountButton(Res.drawable.arrow_up) {
                component.increment()
                output(model.count.toLong())
            }
            CountButton(Res.drawable.arrow_down) {
                component.decrement()
                output(model.count.toLong())
            }
        }
    }
}

@Composable
private fun CountButton(resource: DrawableResource ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(34.dp)
            .clickable { onClick() }
            .background(color = Colors.accentColor),
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