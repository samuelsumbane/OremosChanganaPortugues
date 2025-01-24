package com.samuel.oremoschanganapt.view.morepagesPackage

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.PrayRow
import com.samuel.oremoschanganapt.components.SearchContainer
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuelsumbane.oremoschanganapt.db.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LovedDataPage(
    navController: NavController,
    prayViewModel: PrayViewModel,
    songViewModel: SongViewModel,
    commonViewModel: CommonViewModel
){
    var searchValue by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT


    val lovedData by commonViewModel.lovedData.collectAsState()
    val lPrays = mutableListOf<Pray>()
    val lSongs = mutableListOf<Song>()

    lovedData.forEach {
        when (it.tableName) {
            "Pray" -> {
                val pray = prayViewModel.getPrayById(it.lovedDataId)
                lPrays.add(pray!!)
            }

            "Song" -> {
                val song = songViewModel.getSongById(it.lovedDataId)
                lSongs.add(song!!)
            }

            else ->  Log.d("LovedDataError", "unknown table")
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
                    searchValue = SearchContainer(searchValue, "Pesquisar favoritos")
                }
            )
        },
        bottomBar = {
            if (isPortrait) {
                BottomAppBarPrincipal(navController, "morepages")
            }
        }
    ) { paddingVales ->

//        Column(modifier = Modifier.fillMaxSize().padding(paddingVales),
//        ){
        Row(Modifier.fillMaxSize().padding(paddingVales)) {
            if (!isPortrait) {
                SidebarNav(navController, "canticosAgrupados")
            }

            if (lovedData.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Nenhuma oração ou cântico encontrado.", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                }
            } else  {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items (filteredPrays) { oracao ->
                        PrayRow(
                            commonViewModel,
                            navController, oracao
                        )
                    }
                    
                    items (filteredSongs) { cancao ->
                        SongRow(
                            commonViewModel,
                            navController,
                            cancao, cancao.songId
                        )
                    }
                }
            }
        }

        ShortcutsButton(navController)
    }
}
