package ru.chaglovne.l2.components.profile.ui_logic.login

import EventManager
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.dragon_slayer.firebase.impl.FirebaseAuth
import com.dragon_slayer.firebase.utils.onError
import com.dragon_slayer.firebase.utils.onSuccess
import com.dragon_slayer.firebase.utils.toMessage
import data.User
import kotlinx.coroutines.*
import ru.chaglovne.l2.components.input.ui_logic.DefaultTextInputComponent
import ru.chaglovne.l2.components.input.ui_logic.TextInputComponent

class DefaultLoginComponent(
    private val componentContext: ComponentContext,
    private val firebaseAuth: FirebaseAuth,
    private val goToSelectedAvatar: (User) -> Unit
): LoginComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val _model = MutableValue(LoginComponent.Model())
    override val model: MutableValue<LoginComponent.Model>
        get() = _model
    override val loginInput: TextInputComponent
        get() = DefaultTextInputComponent(
            componentContext = componentContext,
            initText = _model.value.login,
            placeholder = "Логин"
        ) { change ->
            _model.update {
                it.copy(
                    login = change
                )
            }
        }
    override val passwordInput: TextInputComponent
        get() = DefaultTextInputComponent(
            componentContext = componentContext,
            initText = _model.value.password,
            placeholder = "Пароль"
        ) { change ->
            _model.update {
                it.copy(
                    password = change
                )
            }
        }

    override fun registration(login: String, password: String) {
        scope.launch {
            firebaseAuth
                .register(
                    userName = login,
                    password = password
                )
                .onSuccess { user ->
                    _model.update {
                        it.copy(
                            isUserAuth = true,
                            registrationSwitch = false,
                            user = user
                        )
                    }
                    goToSelectedAvatar(user)
                }
                .onError { error ->
                    EventManager
                        .sendMessage(error.toMessage())
                }
        }
    }

    override fun authorization(login: String, password: String) {
        scope.launch {
            firebaseAuth
                .authorization(
                    userName = login,
                    password = password
                )
                .onSuccess { user ->
                    _model.update {
                        it.copy(
                            isUserAuth = true,
                            registrationSwitch = false,
                            user = user
                        )
                    }
                    goToSelectedAvatar(user)
                }
                .onError { error ->
                    EventManager
                        .sendMessage(error.toMessage())
                }
        }
    }

    override fun onRegistrationSwitchChange(value: Boolean) {
        _model.update {
            it.copy(
                registrationSwitch = value
            )
        }
    }
}