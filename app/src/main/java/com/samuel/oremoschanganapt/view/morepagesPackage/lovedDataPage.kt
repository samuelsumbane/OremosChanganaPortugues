package com.samuel.oremoschanganapt.view.morepagesPackage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.PrayRow
import com.samuel.oremoschanganapt.components.searchContainer
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.getIdSet
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoschanganapt.db.data.Pray
import com.samuelsumbane.oremoschanganapt.db.data.praysData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LovedDataPage(navController: NavController) {
    var searchValue by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    var lPrays by remember { mutableStateOf(mutableListOf<Pray>()) }
    val lSongs by remember { mutableStateOf(mutableListOf<Song>()) }
    var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }
    var lovedPraysIds by remember { mutableStateOf(setOf<Int>()) }

    val context = LocalContext.current

    LaunchedEffect(lovedSongsIds) {
        lovedSongsIds = getIdSet(context, "songs_id_set")
        lovedSongsIds.forEach { id ->
            songsData
                .firstOrNull { it.id == id }
                ?.let { song -> lSongs.add(song) }
        }
    }

    LaunchedEffect(lovedPraysIds) {
        lovedPraysIds = getIdSet(context, "prays_id_set")

        lovedPraysIds.forEach { id ->
            praysData
                .firstOrNull { it.id == id }
                ?.let {  pray -> lPrays.add(pray) }
        }

    }

    val filteredPrays = remember(lPrays, searchValue){
        if (searchValue.isNotEmpty()) {
            lPrays.filter { it.title.contains(searchValue, ignoreCase = true) }
        } else lPrays
    }

    val filteredSongs = remember(lSongs, searchValue){
        if (searchValue.isNotBlank()) {
            val numOrNot = isNumber(searchValue)
            if (numOrNot) {
                lSongs.filter { it.number == searchValue }
            } else {
                lSongs.filter {
                    it.title.contains(searchValue, ignoreCase = true)
                }
            }
        } else lSongs
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Favoritos", color = MaterialTheme.colorScheme.tertiary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    searchValue = searchContainer(searchValue, "Pesquisar favoritos")
                }
            )
        },
        bottomBar = {
            if (isPortrait) BottomAppBarPrincipal(navController, "morepages")
        }
    ) { paddingVales ->

        Row(Modifier.padding(paddingVales).fillMaxSize()) {
            if (!isPortrait) SidebarNav(navController, "canticosAgrupados")

            if (lPrays.isEmpty() && lSongs.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Nenhuma oração ou cântico encontrado.", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                }
            } else  {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (filteredSongs.isNotEmpty()) {
                        item { ColumnDivider(if (filteredSongs.size == 1) "Cântico" else "Cânticos") }
                        items (filteredSongs) { song ->
                            SongRow(navController, song, reloadIcon = false)
                        }
                    }
                    if (filteredPrays.isNotEmpty()) {
                        item { ColumnDivider(if (filteredPrays.size == 1) "Oração" else "Orações") }
                        items (filteredPrays) { pray ->
                            PrayRow(navController, pray = pray, reloadIcon = false)
                        }
                    }

                }
            }
        }

        ShortcutsButton(navController)
    }
}

@Composable
fun ColumnDivider(text: String) {
    val mainColor = ColorObject.mainColor
    Column(
        Modifier.fillMaxWidth()
            .drawWithContent {
                drawContent()
                drawLine(
                    color = mainColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 3.dp.toPx(),
                )
            }
    ) {
        Text(text, Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp))
    }
}