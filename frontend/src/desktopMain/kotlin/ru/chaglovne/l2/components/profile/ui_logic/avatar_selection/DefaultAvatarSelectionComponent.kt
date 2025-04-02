package ru.chaglovne.l2.components.profile.ui_logic.avatar_selection

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.dragon_slayer.avatars_api.AvatarsApiImpl
import data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultAvatarSelectionComponent(
    componentContext: ComponentContext,
    user: User,
    handleBackPress: () -> Unit,
    goToProfileScreen: (User) -> Unit
): AvatarSelectionComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val _model = MutableValue(AvatarSelectionComponent.Model())
    override val model: MutableValue<AvatarSelectionComponent.Model>
        get() = _model

    init {
        getAvatars()
    }

    override fun getAvatars() {
        val api = AvatarsApiImpl()
        scope.launch {
            val avatars = api.getAvatars()
            _model.update {
                it.copy(
                    isLoading = false,
                    avatarList = avatars
                )
            }
        }
    }
}