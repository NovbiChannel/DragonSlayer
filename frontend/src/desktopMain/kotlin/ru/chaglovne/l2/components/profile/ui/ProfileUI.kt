package ru.chaglovne.l2.components.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import l2macros.frontend.generated.resources.Res
import l2macros.frontend.generated.resources.character
import l2macros.frontend.generated.resources.logo_vk_color_24
import org.jetbrains.compose.resources.painterResource
import ru.chaglovne.l2.components.profile.ui_logic.ProfileComponent
import ru.chaglovne.l2.theme.Colors

@Composable
fun ProfileContent(component: ProfileComponent) {
    UserIsNotAuth(component)
}

@Composable
fun UserIsNotAuth(component: ProfileComponent) {
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
                onClick = { component.openUrlInBrowser("http://localhost:63342/L2Macros/webCallback/build/webCallback/index.html?code=psdf&state=sdfsd&device_id=pos123") },
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