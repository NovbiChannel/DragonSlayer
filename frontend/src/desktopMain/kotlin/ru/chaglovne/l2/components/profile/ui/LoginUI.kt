package ru.chaglovne.l2.components.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dragonslayerfrontend.frontend.generated.resources.Res
import dragonslayerfrontend.frontend.generated.resources.character
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.input.ui.TextInputUI
import ru.chaglovne.l2.components.profile.ui_logic.login.LoginComponent
import ru.chaglovne.l2.compose_ui.AccentButton
import ru.chaglovne.l2.theme.Colors

@Composable
fun LoginContent(component: LoginComponent) {
    val model by component.model.subscribeAsState()

    val TextInputHeight = 43.dp
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.character),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
            Text(
                text = "Авторизуйся, чтобы подключить новые\n" +
                        "возможности и не потерять свои макросы, или\n" +
                        "зарегистрируйся, если профиля ещё нет",
                color = Colors.textColor,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextInputUI(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TextInputHeight)
                    ,
                    component = component.loginInput
                )
                TextInputUI(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TextInputHeight)
                    ,
                    component = component.passwordInput,
                    isPasswordInput = true
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Switch(
                        checked = model.registrationSwitch,
                        onCheckedChange = { value ->
                            component.onRegistrationSwitchChange(value)
                        },
                        colors = SwitchDefaults.colors(

                        )
                    )
                    Text(
                        text = "Зарегистрироваться",
                        color = Colors.textColor,
                        fontSize = 12.sp
                    )
                }
                val buttonTitle = if (model.registrationSwitch) "Зарегистрироваться" else "Войти"
                AccentButton(
                    title = buttonTitle,
                    isSelected = true,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (model.registrationSwitch) {
                        component.registration(
                            login = model.login,
                            password = model.password
                        )
                    } else {
                        component.authorization(
                            login = model.login,
                            password = model.password
                        )
                    }
                }
            }
        }
    }
}