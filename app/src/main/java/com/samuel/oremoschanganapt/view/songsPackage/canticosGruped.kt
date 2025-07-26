package com.samuel.oremoschanganapt.view.songsPackage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.db.data.groupValues
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.repository.ColorObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanticosAgrupados( navController: NavController) {

    val allSongs = songsData
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.grouped_songs), color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={navController.popBackStack()}) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            if (isPortrait) BottomAppBarPrincipal(navController, "canticosAgrupados")
        }

        ) { paddingVales ->

        val mainColor = ColorObject.mainColor
        val secondColor = ColorObject.secondColor

            if(allSongs.isNotEmpty()){
                Row(Modifier.padding(paddingVales).fillMaxSize()) {
                    if (!isPortrait) SidebarNav(navController, "canticosAgrupados")

                    LazyColumn(
                        modifier = Modifier.weight(1f).padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item(1) {
                            groupValues.forEach { group ->
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .height(65.dp)
                                        .border(1.2.dp, mainColor, shape = RoundedCornerShape(14.dp))
                                        .clickable {
                                            navController.navigate("canticospage/${group.key}/${group.value}")
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(0.9f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = group.value
                                                .uppercase()
                                                .replace(" | ", "\n"),
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.tertiary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Icon(Icons.Default.ArrowForward, contentDescription = "to go collection")
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                LoadingScreen()
            }
    }
}

