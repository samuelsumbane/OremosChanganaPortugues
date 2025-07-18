package com.samuel.oremoschanganapt.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.ui.theme.DarkSecondary
import com.samuel.oremoschanganapt.ui.theme.LightSecondary

@Composable
fun SidebarNav(navController: NavController, activePage: String, modifier: Modifier = Modifier,iconColorState: String = "Keep", ) {
    NavigationRail(
        modifier = if (activePage == "home") modifier else Modifier.padding(0.dp).width(80.dp),
        containerColor = Color.Transparent,
    ) {
        val bottomBgColor = if (isSystemInDarkTheme()) LightSecondary else DarkSecondary

        Card(
            modifier = Modifier
                .fillMaxSize().width(85.dp)
                .padding(10.dp, 0.dp, 10.dp, 7.dp)
                .background(bottomBgColor,
                    shape = RoundedCornerShape(15.dp)),
            elevation = CardDefaults.elevatedCardElevation(3.dp)
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                MenuContent(navController, activePage, iconColorState)
            }
        }
    }
}

@Composable
fun BottomAppBarPrincipal(navController: NavController, activePage: String, iconColorState: String = "Keep") {
    BottomAppBar(
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(75.dp).fillMaxWidth()
    ) {
        val bottomBgColor = if (isSystemInDarkTheme()) LightSecondary else DarkSecondary
        Card(
            modifier = Modifier
                .fillMaxSize().height(60.dp)
                .background(Color.Transparent)
                .padding(10.dp, 0.dp, 10.dp, 7.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = bottomBgColor
            )
        ) {
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MenuContent(navController, activePage, iconColorState)
            }
        }
    }
}


@Composable
fun MenuContent(navController: NavController, activePage: String, iconColorState: String = "Keep") {

    var mainColor by remember { mutableStateOf(ColorObject.mainColor) }

    LaunchedEffect(iconColorState) {
        if (iconColorState == "Reload") mainColor = ColorObject.mainColor // Updates icons color -------->>
    }

    val pages = listOf("home", "oracoespage", "canticosAgrupados", "morepages")
    val btnText = mapOf("home" to "Ínicio", "oracoespage" to "Orações", "canticosAgrupados" to "Cânticos", "morepages" to "Mais")
    val btnIcons = mapOf(
        "home" to Icons.Filled.Home,
        "oracoespage" to ImageVector.vectorResource(id = R.drawable.prayicon),
        "canticosAgrupados" to ImageVector.vectorResource(id = R.drawable.ic_music),
        "morepages" to Icons.Outlined.Add
    )

    pages.forEach {
        Box(
            modifier = Modifier
                .clickable { navController.navigate(it) }
                .padding(8.dp)
                .drawWithContent {
                    drawContent()
                    if (activePage == it) {
                        drawLine(
                            color = lerp(
                                ColorObject.mainColor, Color.White, 0.35f
                            ),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 3.dp.toPx(),
                        )
                    }
                }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = btnIcons[it]!!,
                    contentDescription = "",
                    modifier = Modifier.size(25.dp),
                    tint = mainColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = btnText[it]!!,
                    modifier = Modifier.padding(2.dp),
                    color = Color.Black, fontSize = 13.sp
                )
            }
        }
    }
}