package com.samuel.oremoschanganapt.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.PrayRow
import com.samuel.oremoschanganapt.components.searchContainer
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.repository.TablesViewModels
import com.samuel.oremoschanganapt.ui.theme.DarkSecondary
import com.samuel.oremoschanganapt.ui.theme.LightSecondary
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import kotlinx.coroutines.coroutineScope
import kotlin.math.roundToInt


@Composable
fun ShortcutsButton(navController: NavController) {

    var isActive by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toDouble()
    val screenPercent = 10 * screenHeight / 100
    var searchValue by remember { mutableStateOf("") }

    val prayViewModel = PrayViewModel()
    val songViewModel = SongViewModel()

    val allPrays by TablesViewModels.prayViewModel!!.prays.collectAsState()
    val allSongs by TablesViewModels.songViewModel!!.songs.collectAsState()

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

    Box(Modifier.fillMaxSize().background(Color.Transparent)) {

        var offsetY by remember { mutableStateOf(screenHeight) }
        val childColumnHeight by remember { mutableIntStateOf(230) }
        var showSearchModal by remember { mutableStateOf(false) }

        LaunchedEffect(offsetY) {
            coroutineScope {
                if(offsetY < screenPercent){
                    offsetY = screenPercent - 10
                } else if ( offsetY > screenHeight * 1.20){
                    offsetY = screenHeight * 1.17
                }
            }
        }

        if (showSearchModal) {
            Column(
                Modifier
                    .fillMaxHeight(0.88f)
                    .fillMaxWidth()
                    .padding(start=12.dp, top=80.dp, end = 12.dp)
                    .zIndex(4.0f)
                    .background(Color.Black.copy(alpha=0.97f), RoundedCornerShape(5)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Row {
                    Column(Modifier.padding(top = 10.dp)) {
                        searchValue = searchContainer(
                            searchString = searchValue,
                            searchInputLabel = "Cântico / Oração",
                            isContainerActive = true,
                            showIcon = false
                        )
                    }

                    IconButton(
                        modifier = Modifier.padding(top = 6.dp),
                        onClick = {showSearchModal = !showSearchModal}
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "close",
                            tint=MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(32.dp))
                    }
                }

                LazyColumn(
                    Modifier
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.95f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items (filteredPrays) { PrayRow(navController, prayViewModel, it) }
                    items (filteredSongs) { SongRow(navController, songViewModel, it) }
                }
            }
        }

        Row(
            Modifier.align(Alignment.TopEnd)
                .fillMaxHeight()
                .zIndex(10.0f)
                .offset {IntOffset(0, offsetY.roundToInt())}
        ) {
            // Leftpart (Songs icon, Pray icon, star icon) -------->>
            AnimatedVisibility(isActive) {
                Column(
                    Modifier.width(80.dp)
                        .height(childColumnHeight.dp)
                        .padding(end = 5.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    // Songs
                    ShortcutButtonChild(
                        modifier = Modifier.align(Alignment.End),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_music),
                        description = "Canticos",
                        iconModifier = Modifier.size(26.dp)
                    ){  navController.navigate("canticosAgrupados") }

                    Spacer(Modifier.height(10.dp))

                    // Prays
                    ShortcutButtonChild(
                        icon = ImageVector.vectorResource(id = R.drawable.prayicon),
                        description = "Oracoes",
                        iconModifier = Modifier.size(26.dp)
                    ){ navController.navigate("oracoespage") }

                    Spacer(Modifier.height(10.dp))

                    ShortcutButtonChild(
                        modifier = Modifier.align(Alignment.End),
                        icon = Icons.Outlined.Star,
                        description = "Favoritos"
                    ) {  navController.navigate("favoritospage") }

                }
            }

            Column(
                Modifier.width(50.dp)
                    .height(childColumnHeight.dp),
                verticalArrangement = Arrangement.SpaceAround
            ){
                AnimatedVisibility(isActive) {
                    ShortcutButtonChild(
                        modifier = Modifier.align(Alignment.End),
                        icon = Icons.Default.Search,
                        description = "Search"
                    ) {showSearchModal = !showSearchModal; isActive = !isActive}
                }
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
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(48.dp)
                    )
                }

                AnimatedVisibility(isActive) {
                    ShortcutButtonChild(
                        modifier = Modifier.align(Alignment.End),
                        icon = Icons.Default.Home,
                        description = "Home"
                    ) {navController.navigate("home")}
                }
            }
            Spacer(Modifier.width(5.dp))
        }
    }
}
