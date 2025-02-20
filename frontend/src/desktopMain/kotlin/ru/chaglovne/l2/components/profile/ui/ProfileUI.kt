package ru.chaglovne.l2.components.profile.ui

import ApiClient
import ApiParams
import DragonSlayerAPI
import EventManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import extention.parseQueryString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.character
import l2macros.frontend.generated.resources.logo_vk_color_24
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.profile.ui_logic.ProfileComponent
import ru.chaglovne.l2.theme.Colors
import ru.dragonslayer.webview.WebViewSharedFlow
import ru.dragonslayer.webview.launchWebView
import java.net.URI
import java.net.URL

@Composable
fun ProfileContent(component: ProfileComponent) {
    val isUserAuth by component.isUserAuth.subscribeAsState()
    val scope = rememberCoroutineScope()
    var authUrl by remember { mutableStateOf<String?>(null) }
    var showWebViewDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        WebViewSharedFlow.urlFlow.collect { newUrl ->
            if (newUrl.startsWith("https://dragonslayerauth.netlify.app/")) {
                val uri = URI(newUrl)
                uri.query.parseQueryString().forEach {
                    println("${it.key} - ${it.value}")
                }
                val queryMap = uri.query.parseQueryString()

                val code = queryMap[ApiParams.CODE]?: ""
                val state = queryMap[ApiParams.STATE]?: ""
                val deviceId = queryMap[ApiParams.DEVICE_ID]?: ""

                component.postAuthParams(code, state, deviceId)
            }
        }
    }

    scope.launch {
        component.flow.collect { authData ->
            when (authData.type) {
                DragonSlayerAPI.DataType.AuthError -> EventManager.sendMessage("Упс... Не удалось пройти авторизацию, попробуй позже")
                DragonSlayerAPI.DataType.AuthSuccess -> {
                    EventManager.sendMessage("Авторизация успешна!")
                    println(authData.data)
                }
                DragonSlayerAPI.DataType.SendAuthUrl -> {
                    authUrl = authData.data
                    showWebViewDialog = true
                }
                DragonSlayerAPI.DataType.UnknownType -> EventManager.sendMessage("Упс... Что-то пошло не так")
            }
        }
    }

    if (isUserAuth) UserIsAuth(component) else UserIsNotAuth(component, scope)
    if (showWebViewDialog && authUrl != null) launchWebView(authUrl!!, scope)
}

@Composable
fun UserIsAuth(component: ProfileComponent) {

}

@Composable
fun UserIsNotAuth(component: ProfileComponent, scope: CoroutineScope) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.character),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
            )
            Text(
                text = "Войди с VK ID, чтобы подключить новые\nвозможности и не потерять свои макросы",
                color = Colors.textColor,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Button(
                onClick = { scope.launch(Dispatchers.IO) { component.getAuthUrl() } },
                modifier = Modifier.width(350.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Colors.textColor,
                    contentColor = Color.Black
                )
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo_vk_color_24),
                    contentDescription = null
                )
                Text(
                    text = "Войти с VK ID",
                    color = Color.Black,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}