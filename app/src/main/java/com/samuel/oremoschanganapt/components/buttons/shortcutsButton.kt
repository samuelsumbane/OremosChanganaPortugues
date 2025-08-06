package com.samuel.oremoschanganapt.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.SetIdPreference
import com.samuel.oremoschanganapt.components.PrayRow
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.components.searchContainer
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.getIdSet
import com.samuel.oremoschanganapt.saveIdSet
import com.samuel.oremoschanganapt.ui.theme.DarkSecondary
import com.samuel.oremoschanganapt.ui.theme.LightSecondary
import com.samuel.oremoschanganapt.db.data.praysData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ShortcutsButton(navController: NavController) {

    var isActive by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toDouble()
    val screenPercent = 10 * screenHeight / 100
    var searchValue by remember { mutableStateOf("") }

    val allPrays = praysData
    val allSongs = songsData

    val filteredPrays = remember(allPrays, searchValue){
        if (searchValue.isNotEmpty()) {
            allPrays.filter { it.title.contains(searchValue, ignoreCase = true)}
        } else {
            emptyList()
        }
    }

    val filteredSongs = remember(allSongs, searchValue){
        if (searchValue.isNotBlank()) {
            val numOrNot = isNumber(searchValue)
            if (numOrNot) {
                allSongs.filter { it.number == searchValue }
            } else {
                allSongs.filter {
                    it.title.contains(searchValue,ignoreCase = true)
                }
            }
        } else { emptyList() }
    }

    Box(
        modifier = Modifier
        .fillMaxSize()
//        .background(Color.Magenta)
    ) {
        var offsetY by remember { mutableDoubleStateOf(screenHeight) }
        val childColumnHeight by remember { mutableIntStateOf(150) }
        val context = LocalContext.current

        var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
        var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }

        LaunchedEffect(Unit) {
            lovedIdPrays = getIdSet(context, SetIdPreference.PRAYS_ID.preferenceName)
            lovedSongsIds = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
        }

        LaunchedEffect(offsetY) {
            coroutineScope {
                if(offsetY < screenPercent){
                    offsetY = screenPercent - 10
                } else if ( offsetY > screenHeight * 1.20){
                    offsetY = screenHeight * 1.17
                }
            }
        }

        Row(
            Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .zIndex(10.0f)
                .offset {IntOffset(0, offsetY.roundToInt())}
        ) {

            Row(
                modifier = Modifier.height(childColumnHeight.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leftpart (Songs icon, Pray icon, star icon) -------->>
                AnimatedVisibility(isActive) {
                    Column(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .width(140.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.86f),
                                shape = RoundedCornerShape(10)
                            ),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(
                            Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Songs
                            ShortcutButtonChild(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_music),
                                description = "Canticos",
                                iconModifier = Modifier.size(26.dp)
                            ) {  navController.navigate("canticosAgrupados") }

                            // Prays
                            ShortcutButtonChild(
                                icon = ImageVector.vectorResource(id = R.drawable.prayicon),
                                description = "Prays",
                                iconModifier = Modifier.size(26.dp)
                            ) { navController.navigate("oracoespage") }
                        }

                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ShortcutButtonChild(
                                icon = Icons.Outlined.Star,
                                description = "Favoritos"
                            ) {  navController.navigate("favoritospage") }

                            ShortcutButtonChild(
                                icon = Icons.Default.Home,
                                description = "Home"
                            ) {navController.navigate("home")}
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Column {
                    val bgColor = if (!isSystemInDarkTheme()) LightSecondary else DarkSecondary

                    IconButton(
                        modifier = Modifier
                            .size(50.dp)
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    offsetY += dragAmount.y
                                }
                            },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = bgColor),
                        onClick = { isActive = !isActive }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.adjust_24),
                            contentDescription = "Shortcuts",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.width(5.dp))
        }
    }
}
