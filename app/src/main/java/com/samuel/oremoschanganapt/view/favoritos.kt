package com.samuel.oremoschanganapt.view

//import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.InputPesquisa
import com.samuel.oremoschanganapt.components.SearchContainer
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosPage(
    navController: NavController,
    prayViewModel: PrayViewModel,
    songViewModel: SongViewModel
){
    var pesquisaTexto by remember { mutableStateOf("") }

    val songs by songViewModel.songs.collectAsState()
    val lSongs = songs.filter { it.loved }

    val prays by prayViewModel.prays.collectAsState()
    val lPrays = prays.filter { it.loved }
    

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
//                    InputPesquisa(
//                        value = pesquisaTexto,
//                        onValueChange = { pesquisaTexto = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(50.dp, 0.dp, 20.dp, 10.dp)
//                            .height(58.dp),
//                        label = "Pesquisar loveds",
//                        maxLines = 1,
//                    )

                    pesquisaTexto = SearchContainer(pesquisaTexto, "Pesquisar favoritos")

                }
            )
        },
//        bottomBar = {
//            BottomAppBarPrincipal(navController, "lovedspage")
//        }
    ){paddingVales ->
        val mainColor = colorObject.mainColor

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingVales),
        ){
            if (lSongs.isEmpty() && lPrays.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Nenhuma oração ou cântico encontrado.", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                }
            }else if (lSongs.isNotEmpty() || lPrays.isNotEmpty()) {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        if (pesquisaTexto.isNotBlank()) {
                            lPrays.filter {
                                it.title.contains(
                                    pesquisaTexto, ignoreCase = true
                                )
                            }
                        } else { lPrays }
                    ) { oracao ->
                        val prayTitle = oracao.title
                        val prayBody = oracao.body

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(60.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(mainColor)
                                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                .clickable {
                                    navController.navigate("eachOracao/${oracao.prayId} ")
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize()
                                    .weight(0.9f)
                                    .fillMaxHeight()
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = oracao.title,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = oracao.subTitle,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.1f)
                                    .height(60.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.1f)
                                        .height(60.dp),
                                    onClick = {
                                        val status = if (oracao.loved){
                                            false
                                        }else{
                                            true
                                        }
                                        prayViewModel.updatePray(oracao.prayId, status)
                                    }
                                ){
                                    Icon(imageVector = Icons.Default.Star, contentDescription = "É loved", tint = Orange)
                                }
                            }
                        }
                    }


                    items(
                        if (pesquisaTexto.isNotBlank()) {
                            //
                            val numOrNot = isNumber(pesquisaTexto)
                            if (numOrNot) {
                                lSongs.filter { it.number == pesquisaTexto }
                            } else {
                                lSongs.filter {
                                    it.title.contains(
                                        pesquisaTexto,ignoreCase = true
                                    )
                                }
                            }
                        } else { lSongs }
                    ) { cancao ->

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(60.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(mainColor)
                                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                .clickable {
                                    navController.navigate("eachCantico/${cancao.songId} ")
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.9f)
                                    .fillMaxHeight()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.1f)
                                        .height(60.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
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

                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.1f)
                                    .height(60.dp),

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.1f)
                                        .height(60.dp),
                                    onClick = {
                                        val status = if (cancao.loved){
                                            false
                                        }else{
                                            true
                                        }
                                        songViewModel.updateSong(cancao.songId, status)
                                    }
                                ){
                                    Icon(imageVector = Icons.Default.Star, contentDescription = "É loved", tint = Orange)
                                }
                            }
                        }
                    }
                }
            }
        }

        ShortcutsButton(navController)

    }

}

