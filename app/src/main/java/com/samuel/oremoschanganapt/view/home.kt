package com.samuel.oremoschanganapt.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
//import com.samuel.oremoschanganapt.apresentacaoOracao.*
import com.samuel.oremoschanganapt.components.* 
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.ui.theme.Dodgerblue
import com.samuel.oremoschanganapt.ui.theme.HomeColor
import com.samuel.oremoschanganapt.ui.theme.grayHomeColor
import com.samuel.oremoschanganapt.view.sideBar.About
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home( navController: NavController, songViewModel: SongViewModel, prayViewModel: PrayViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textInputValue by remember { mutableStateOf("") }


    val allSongs by songViewModel.songs.collectAsState()
    val allPrays by prayViewModel.prays.collectAsState()

    var showModal by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                About()
            }
        }
    ) {
        Scaffold( bottomBar = { BottomAppBarPrincipal(navController, "home") }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HomeColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.homepic),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.3f)
                        .background(
                            color = grayHomeColor,
                            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 40.dp),
                        ),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        Modifier.fillMaxWidth()
//                            .background(Color.Red)
                    ){
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu",
                                tint = Color.White, modifier = Modifier.size(30.dp))
                        }
                    }

                    InputPesquisa(
                        value = textInputValue,
                        onValueChange = {
                            textInputValue = it
                            showModal = textInputValue != ""
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(58.dp),
                        label = "Pesquisar Cântico / Oração",
                        maxLines = 1,
                    )

                    Column{
                        Text(
                            text = "Oremos",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 45.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A HI KHONGELENI",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }
                }

                if (showModal){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.90f)
                            .heightIn(min = 90.dp, max = 500.dp)
                            .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(15.dp))
                            .border(0.7.dp, Dodgerblue, RoundedCornerShape(15.dp))
                            .align(Alignment.CenterEnd),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                if (textInputValue.isNotBlank()) {
                                    allPrays.filter {
                                        it.title.contains(
                                            textInputValue,
                                            ignoreCase = true
                                        )
                                    }
                                } else {
                                    allPrays.filter { it.title == "0000" } // invalid number, in order to clean the list
                                }
                            ){  oracao ->
                                val prayTitle = oracao.title
                                val prayBody = oracao.body

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(45.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                        .clickable {
                                            navController.navigate("eachOracao/${prayTitle}/${prayBody}")
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(0.9f)
                                            .fillMaxHeight()
                                    ) {

                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = prayTitle,
                                                fontSize = 18.sp,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = "Oração",
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }

                            items(
                                if (textInputValue.isNotBlank()) {
                                    val numOrNot = isNumber(textInputValue)
                                    if (numOrNot) {
                                        allSongs.filter { it.number == textInputValue }
                                    } else {
                                        allSongs.filter {
                                            it.title.contains(
                                                textInputValue,
                                                ignoreCase = true
                                            )
                                        }
                                    }
                                } else {
                                    allSongs.filter { it.number == "0000" } // invalid number, in order to clean the list
                                }

                            ) { cantico ->
                                val songTitle = cantico.title
                                val songBody = cantico.body
                                val songNumber = cantico.number

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(45.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                        .clickable {
                                            navController.navigate("eachCantico/${songNumber}/${songTitle}/${songBody} ")
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(0.9f)
                                            .fillMaxHeight()
                                    ) {

                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = songTitle,
                                                fontSize = 18.sp,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = "Cântico: $songNumber",
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }

                }

            }
        }
    }
}
