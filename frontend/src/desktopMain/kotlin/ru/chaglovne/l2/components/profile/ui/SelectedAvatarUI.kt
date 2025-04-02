package ru.chaglovne.l2.components.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.profile.ui_logic.avatar_selection.AvatarSelectionComponent
import ru.chaglovne.l2.theme.Colors

@Composable
fun AvatarSelectionContent(component: AvatarSelectionComponent) {
    val model by component.model.subscribeAsState()

    AvatarList(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
        ,
        model = model
    )
}

@Composable
private fun AvatarList(
    modifier: Modifier = Modifier,
    model: AvatarSelectionComponent.Model
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (model.isLoading) {
            items(count = 25) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Colors.secondaryContainer,
                            shape = CircleShape
                        )
                )
            }
        } else {
            items(model.avatarList) { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}