package com.samuel.oremoschanganapt.view.morepagesPackage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.buttons.MorePagesBtn
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.ui.theme.Typography


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MorePages(navController: NavController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    Scaffold(
       bottomBar = { if (isPortrait) BottomAppBarPrincipal(navController, "morepages") }
    ) { paddingValues ->
        Row(Modifier.fillMaxSize().padding(paddingValues)) {
            if (!isPortrait) SidebarNav(navController, "canticosAgrupados")
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.more_pages), style = Typography.titleLarge, fontStyle = FontStyle.Italic)
                Column(Modifier.fillMaxWidth(0.80f)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MorePagesBtn(
                            icon = Icons.Default.Warning,
                            description = "pagina de apêndice",
                            text = "Apêndice", Modifier.weight(1f)) {
                            navController.navigate("apendice")
                        }

                        Spacer(Modifier.width(25.dp))

                        MorePagesBtn(
                            icon = Icons.Default.Notifications,
                            description = "Pagina de festas móveis",
                            text = "Festas Móveis", Modifier.weight(1f)) {
                            navController.navigate("festasmoveis")
                        }
                    }

                    Spacer(Modifier.height(25.dp))

                    Row(Modifier.fillMaxWidth()) {
                        MorePagesBtn(
                            icon = Icons.Default.DateRange,
                            description = "Pagina de liccionario",
                            text = "Leccionário", Modifier.weight(1f)) {
                            navController.navigate("licionario")
                        }
                        Spacer(Modifier.width(25.dp))

                        MorePagesBtn(
                            icon = Icons.Default.Star,
                            description = "Pagina de orações e cânticos favoritos",
                            text = "Favoritos", Modifier.weight(1f)) {
                            navController.navigate("favoritospage")
                        }
                    }

                    Spacer(Modifier.height(25.dp))

                    Row(
                        Modifier.fillMaxWidth().height(95.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MorePagesBtn(
                            icon = Icons.Default.Notifications,
                            description = "pagina de lembretes",
                            text = "Lembretes") {
                            navController.navigate("reminderspage")
                        }
                    }

                }
            }

            ShortcutsButton(navController)
        }

    }
}

