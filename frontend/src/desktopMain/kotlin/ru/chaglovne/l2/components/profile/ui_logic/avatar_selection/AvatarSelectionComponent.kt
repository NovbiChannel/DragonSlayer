package ru.chaglovne.l2.components.profile.ui_logic.avatar_selection

import com.arkivanov.decompose.value.MutableValue

interface AvatarSelectionComponent {
    val model: MutableValue<Model>

    fun getAvatars()

    data class Model(
        val isLoading: Boolean = true,
        val avatarList: List<String> = emptyList()
    )
}