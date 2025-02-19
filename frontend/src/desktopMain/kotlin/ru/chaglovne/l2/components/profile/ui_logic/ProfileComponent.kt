package ru.chaglovne.l2.components.profile.ui_logic

import DragonSlayerAPI
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.flow.MutableSharedFlow

interface ProfileComponent {
    val flow: MutableSharedFlow<DragonSlayerAPI.AuthData>
    val isUserAuth: MutableValue<Boolean>
    fun openUrlInBrowser(url: String)
    suspend fun wsConnect()
}