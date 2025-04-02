package ru.chaglovne.l2.components.profile.ui_logic.avatar_selection

import com.arkivanov.decompose.ComponentContext
import data.User

class DefaultAvatarSelectionComponent(
    componentContext: ComponentContext,
    user: User,
    handleBackPress: () -> Unit,
    goToProfileScreen: (User) -> Unit
): AvatarSelectionComponent, ComponentContext by componentContext {
}