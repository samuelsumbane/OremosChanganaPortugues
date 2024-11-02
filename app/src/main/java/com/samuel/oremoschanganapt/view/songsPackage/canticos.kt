package com.samuel.oremoschanganapt.view.songsPackage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoEvent
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.SearchContainer
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanticosPage(navController: NavController, value: String, songViewModel: SongViewModel){

    var pesquisaTexto by remember { mutableStateOf("") }
    var pesquisaTextoAvancada by remember { mutableStateOf("") }

//    val dados =
    val allSongs by songViewModel.songs.collectAsState()

    val dados = when(value){
        "todos" -> allSongs
        "new" -> allSongs.filter{ it.number.startsWith("0") }
        else -> allSongs.filter{ it.group == value }
    }

//    var activeInput = 0 //normal
    var activeInput by remember { mutableStateOf(0) }

    LaunchedEffect(activeInput) {
        pesquisaTexto = ""
        pesquisaTextoAvancada = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Canticos", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier
//                            .background(Color.Green)
//                            .fillMaxWidth()
                            .padding(50.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        pesquisaTexto = SearchContainer(pesquisatexto = pesquisaTexto)

                        Row(
                            modifier = Modifier.width(50.dp)
                                .padding(0.dp, 7.dp, 0.dp, 0.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    activeInput = if (activeInput == 0) { 1 } else { 0 }
                                },
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

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingVales),
        ){
//            Text(text = "$value")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(
                    if (pesquisaTexto.isNotBlank()) {
//
                        val numOrNot = isNumber(pesquisaTexto)
                        if (numOrNot) {
                            dados.filter { it.number == pesquisaTexto }
                        } else {
                            dados.filter { it.title.contains(pesquisaTexto, ignoreCase = true) }
                        }
                    }else if (pesquisaTextoAvancada.isNotBlank()){
                            val numOrNot = isNumber(pesquisaTextoAvancada)
                            if (numOrNot){
                                dados.filter { it.number == pesquisaTextoAvancada }
                            }else{
                                dados.filter{ it.title.contains(pesquisaTextoAvancada, ignoreCase = true) ||
                                        it.body.contains(pesquisaTextoAvancada, ignoreCase = true)
                                }
                            }
                    } else { dados }

                ) { cancao ->
                    val songId = cancao.songId
                    val mainColor = colorObject.mainColor
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(60.dp)
                            .clip(RoundedCornerShape(14.dp))
//                            .background(MaterialTheme.colorScheme.secondary)
                            .background(mainColor)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .clickable {
                                navController.navigate("eachCantico/${songId}")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.9f)
                                .fillMaxHeight()
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.15f)
                                    .height(60.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = cancao.number,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = cancao.title,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = cancao.subTitle,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        StarButton(
                            itemLoved = cancao.loved,
                            prayViewModel = null,
                            songViewModel = songViewModel,
                            id = cancao.songId,
                            view = "songViewModel"
                        )

                    }
                }
            }
        }

        ShortcutsButton(navController)
    }
}


