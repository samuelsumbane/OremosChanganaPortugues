package com.samuel.oremoschanganapt.view.songsPackage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoEvent
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.SearchContainer
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuelsumbane.oremoschanganapt.db.CommonViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanticosPage(navController: NavController, value: String, readbleValue: String, songViewModel: SongViewModel,
                 commonViewModel: CommonViewModel){

    var searchValue by remember { mutableStateOf("") }
    var pesquisaTextoAvancada by remember { mutableStateOf("") }
    val allSongs by songViewModel.songs.collectAsState()
    var activeInput by remember { mutableIntStateOf(0) }

    val dados = when(value){
        "todos" -> allSongs
        "new" -> allSongs.filter{ it.number.startsWith("0") }
        else -> allSongs.filter{ it.group == value }
    }

    LaunchedEffect(activeInput) {
        searchValue = ""
        pesquisaTextoAvancada = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = readbleValue.replaceFirstChar{char -> char.uppercase()}, color = MaterialTheme.colorScheme.onPrimary) },
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
                        searchValue = SearchContainer(pesquisatexto = searchValue)

                        Row (
                            modifier = Modifier.width(50.dp) .padding(0.dp, 7.dp, 0.dp, 0.dp)
                        ) {
                            IconButton(
                                onClick = { activeInput = if (activeInput == 0) { 1 } else { 0 } },
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_reloadicon),
                                    contentDescription = "Trocar o campo de pesquisa",
                                    modifier = Modifier.size(22.dp),
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBarPrincipal(navController, "canticosAgrupados")
        }
    ){paddingVales ->

        when{
            allSongs.isEmpty() -> LoadingScreen("Canticos")

            else -> {
                val filteredSongs = remember(dados, searchValue){

                    if (searchValue.isNotBlank()) {
                        val numOrNot = isNumber(searchValue)
                        if (numOrNot) {
                            dados.filter { it.number == searchValue }
                        } else {
                            dados.filter {
                                it.title.contains(searchValue, ignoreCase = true)
                            }
                        }
                    } else if (pesquisaTextoAvancada.isNotBlank()){
                            val numOrNot = isNumber(pesquisaTextoAvancada)
                            if (numOrNot){
                                dados.filter { it.number == pesquisaTextoAvancada }
                            } else{
                                dados.filter{ it.title.contains(pesquisaTextoAvancada, ignoreCase = true) ||
                                    it.body.contains(pesquisaTextoAvancada, ignoreCase = true)
                                }
                            }
                    } else { dados }
                }

                // widget
                Column( modifier = Modifier.fillMaxSize().padding(paddingVales) ){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        items( filteredSongs ) { cancao ->
                            val songId = cancao.songId
                            val mainColor = colorObject.mainColor

                            // -------->>
                            SongRow(
                                commonViewModel,
                                navController,
                                cancao, songId
                            )

                        }
                    }
                }

                ShortcutsButton(navController)
            }
        }
    }
}
