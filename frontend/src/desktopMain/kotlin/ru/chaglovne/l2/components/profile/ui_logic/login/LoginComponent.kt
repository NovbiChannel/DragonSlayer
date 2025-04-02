package ru.chaglovne.l2.components.profile.ui_logic.login

import com.arkivanov.decompose.value.MutableValue
import data.User
import ru.chaglovne.l2.components.input.ui_logic.TextInputComponent

interface LoginComponent {
    val model: MutableValue<Model>

    val loginInput: TextInputComponent
    val passwordInput: TextInputComponent

    fun registration(login: String, password: String)
    fun authorization(login: String, password: String)
    fun onRegistrationSwitchChange(value: Boolean)

    data class Model(
        val isUserAuth: Boolean = false,
        val registrationSwitch: Boolean = false,
        val login: String = "",
        val password: String = "",
        val user: User? = null,
    )
}