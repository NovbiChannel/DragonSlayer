package ru.chaglovne.l2.components.macros.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.chaglovne.l2.components.macros.ui_logic.MacrosComponent
import ru.chaglovne.l2.compose_ui.MacroItem

@Composable
fun MacrosContent(component: MacrosComponent) {
    val testList = listOf(
        Pair("Summoner F1 to F6 AOE farm", "`"),
        Pair("Summoner F1 to F6 AOE farm", "P"),
        Pair("Summoner F1 to F6 AOE farm", "H"),
        Pair("Summoner F1 to F6 AOE farm", "L"),
        Pair("Summoner F1 to F6 AOE farm", "MB5"),
        Pair("Summoner F1 to F6 AOE farm", "MB4"),
        Pair("Summoner F1 to F6 AOE farm", "K"),
        Pair("Summoner F1 to F6 AOE farm", "F"),
    )
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(testList) { item ->
            MacroItem(item.first, item.second)
        }
    }
}