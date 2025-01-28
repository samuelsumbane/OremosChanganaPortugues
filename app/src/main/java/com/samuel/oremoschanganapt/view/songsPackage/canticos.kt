package com.samuel.oremoschanganapt.view.songsPackage

import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoEvent
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.OkAlertDialog
import com.samuel.oremoschanganapt.components.SearchContainer
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsPage(navController: NavController, value: String, readbleValue: String, songViewModel: SongViewModel,
              commonViewModel: CommonViewModel
) {

    var searchValue by remember { mutableStateOf("") }
    var advancedSearchString by remember { mutableStateOf("") }
    val allSongs by songViewModel.songs.collectAsState()
    var activeInput by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT


    val data = when(value){
        "todos" -> allSongs
        "new" -> allSongs.filter{ it.number.startsWith("0") }
        else -> allSongs.filter{ it.group == value }
    }

    LaunchedEffect(activeInput) {
        searchValue = ""
        advancedSearchString = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = readbleValue.replaceFirstChar{char -> char.uppercase()}, color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Row ( modifier = Modifier.padding(50.dp, 0.dp, 0.dp, 0.dp) ) {

                        if (activeInput == 0) {
                            searchValue = SearchContainer(searchString = searchValue)
                        } else {
                            advancedSearchString = SearchContainer(searchString = advancedSearchString)
                        }

                        Row (
                            modifier = Modifier.width(50.dp).padding(0.dp, 7.dp, 0.dp, 0.dp)
                        ) {
                            IconButton(
                                onClick = {
                                   if (activeInput == 0){
                                       activeInput = 1
                                       showDialog = true
                                   } else activeInput = 0
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_reloadicon),
                                    contentDescription = "Trocar o campo de pesquisa",
                                    modifier = Modifier.size(24.dp).padding(top=4.dp),
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (isPortrait) {
                BottomAppBarPrincipal(navController, "canticosAgrupados")
            }
        }
    ) { paddingVales ->

        when{
            allSongs.isEmpty() -> LoadingScreen()

            else -> {
                val filteredSongs = remember(data, searchValue, advancedSearchString) {
                    if (searchValue.isNotBlank()) {
                        val numOrNot = isNumber(searchValue)
                        if (numOrNot) {
                            data.filter { it.number == searchValue }
                        } else {
                            data.filter {
                                it.title.contains(searchValue, ignoreCase = true)
                            }
                        }
                    } else if (advancedSearchString.isNotBlank()) {
                        val numOrNot = isNumber(advancedSearchString)
                        if (numOrNot) {
                            data.filter { it.number == advancedSearchString }
                        } else {
                            data.filter {
                                it.title.contains(advancedSearchString, ignoreCase = true) ||
                                        it.body.contains(advancedSearchString, ignoreCase = true)
                            }
                        }
                    } else data
                }

                // widget
//                Column( modifier = Modifier.fillMaxSize().padding(paddingVales) ){
                    //
                    if (showDialog) {
                        OkAlertDialog(
                            onDismissRequest = {showDialog = false},
                            onConfirmation = {showDialog = false},
                            dialogTitle = "Pesquisa avançada activda",
                            dialogText = "Pode pesquisar o cântico por conteúdo\n dentro do cântico (corpo do cântico).",
                            Icons.Default.Info
                        )
                    }
                    //
                    Row(Modifier.fillMaxSize().padding(paddingVales)) {
                        if (!isPortrait) {
                            SidebarNav(navController, "canticosAgrupados")
                        }
                        LazyColumn(
                            modifier = Modifier.weight(1f).padding(5.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            items( filteredSongs ) { SongRow(navController, songViewModel, it) }
                        }
                    }

                ShortcutsButton(navController)
            }
        }
    }
}
