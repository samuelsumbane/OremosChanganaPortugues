package com.samuel.oremoschanganapt.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoEvent
import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.apresentacaoOracao.OracaoState
import com.samuel.oremoschanganapt.apresentacaoOracao.OracoesEvent
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.InputPesquisa
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.ui.theme.HomeColor
import com.samuel.oremoschanganapt.ui.theme.Orange
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(state: OracaoState, cstate: CancaoState, navController: NavController, onEvent: (CancaoEvent) -> Unit, onEventO: (OracoesEvent) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textInputValue by remember { mutableStateOf("") }

    val oracoes = state.oracoes
    val canticos = cstate.cancoes
    var showModal by remember { mutableStateOf(false) }
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)
                    ){
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "Oremos Changana - Português",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Programador",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(text = "Samuel Eugénio Sumbane", fontSize = 16.sp,
                                color = Color.Black)
                            Text(text = "Programador Full-Stack", fontSize = 16.sp, 
                                color = Color.Black)
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Contacto",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp , 
                                color = Color.Black
                            )

                            Text(text = "+258 865230661 / +258 833597867", fontSize = (16.sp ), lineHeight = (24.sp ),
                                color = Color.Black)
                            Text(text = "samuelsumbane143@gmail.com", fontSize = (16.sp ), lineHeight = (24.sp ),
                                color = Color.Black)
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Apoio",
                                fontWeight = FontWeight.Bold,
                                fontSize = (16.sp ), lineHeight = (24.sp ),
                                color = Color.Black
                            )
                            Text(
                                text = "A produção deste aplicativo carreceu de alguns custos da parte do programador, neste sentido pretende-se produzir mais aplicativos desta natureza e para tal contamos com seu apoio financeiro que pode ser efectuado através do numero 865230661.",
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(7.dp, 0.dp, 7.dp, 0.dp),
                                fontSize = (16.sp ), lineHeight = (24.sp ),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(text = "Versão: 3.0", color = Color.Black, fontSize = (16.sp ), lineHeight = (24.sp ))
                        }
                    }
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
                Row{
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.3f)
                        .background(
//                            color = Color.Red,
                            color = Color.Transparent,
                            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 40.dp),
                        ),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    InputPesquisa(
                        value = textInputValue,
                        onValueChange = {
                            textInputValue = it
                            showModal = if(textInputValue != "") canticos.isNotEmpty() else false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(top = 25.dp)
//                            .padding(0.dp, 0.dp, 0.dp, 10.dp)
                            .height(58.dp),
                        label = "Pesquisar Cântico / Oração",
                        maxLines = 1,
                    )

                    Column(

                    ){
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
//                            .height(100.dp)
                            .heightIn(min = 90.dp, max = 500.dp)
                            .background(Color.Black.copy(alpha = 0.9f),
                                RoundedCornerShape(15.dp, 0.dp, 0.dp, 15.dp)
                            )
                            .align(Alignment.CenterEnd),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ){
//                    Text("This is text", fontSize = 19.sp, color = Color.White)
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                if (textInputValue.isNotBlank()) {
                                    oracoes.filter {
                                        it.titulo.contains(
                                            textInputValue,
                                            ignoreCase = true
                                        )
                                    }
                                } else {
                                    oracoes.filter { it.titulo == "0000" } // invalid number, in order to clean the list
                                }
                            ){  oracao ->
                                val prayTitle = oracao.titulo
                                val prayBody = oracao.corpo

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(45.dp)
                                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
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
//                            }

                            items(
                                if (textInputValue.isNotBlank()) {
                                    val numOrNot = isNumber(textInputValue)
                                    if (numOrNot) {
                                        canticos.filter { it.numero == textInputValue }
                                    } else {
                                        canticos.filter {
                                            it.titulo.contains(
                                                textInputValue,
                                                ignoreCase = true
                                            )
                                        }
                                    }

                                } else {
                                    canticos.filter { it.numero == "0000" } // invalid number, in order to clean the list
                                }

                            ) { cantico ->
                                val songTitle = cantico.titulo
                                val songBody = cantico.corpo
                                val songNumber = cantico.numero

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(45.dp)
                                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
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
