package com.samuel.oremoschanganapt.view

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
//import com.samuel.oremoschanganapt.apresentacaoOracao.*
import com.samuel.oremoschanganapt.components.*
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.americanFormat
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.functionsKotlin.localTime
import com.samuel.oremoschanganapt.functionsKotlin.restartActivity
import com.samuel.oremoschanganapt.functionsKotlin.stringToColor
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.Dodgerblue
import com.samuel.oremoschanganapt.ui.theme.HomeColor
//import com.samuel.oremoschanganapt.ui.theme.grayHomeColor
import com.samuel.oremoschanganapt.view.sideBar.About
import com.samuel.oremoschanganapt.view.sideBar.AppearanceWidget
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home( navController: NavController, songViewModel: SongViewModel,
          prayViewModel: PrayViewModel, defViewModel: DefViewModel,
          reminderViewModel: ReminderViewModel

) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textInputValue by remember { mutableStateOf("") }

    val allSongs by songViewModel.songs.collectAsState()
    val allPrays by prayViewModel.prays.collectAsState()
    val defs by defViewModel.defs.collectAsState()

    val allR by reminderViewModel.reminders.collectAsState()

    var showModal by remember { mutableStateOf(false) }
    val context = LocalContext.current


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
//                About()

                Spacer(modifier = Modifier.height(30.dp))
                if (defs.isNotEmpty()){
                    val def = defs.first()
                    var themeColor by remember { mutableStateOf(def.themeColor)}
                    var mode by remember { mutableStateOf(def.appMode) }

                    LaunchedEffect(themeColor) {
                        coroutineScope {
                            if (def.themeColor != themeColor){
                                defViewModel.updateDef("themeColor", themeColor)
                                var mainColor = colorObject.mainColor

                                mainColor = stringToColor(themeColor)
                                colorObject.menuContainerColor = lerp(mainColor, Color.Black, 0.3f)
                                colorObject.inputColor = mainColor.copy(alpha = 0.75f)

                            }
                        }
                    }

                    LaunchedEffect(mode) {
                        coroutineScope {
                            defViewModel.updateDef("appMode", mode)
                            if (mode != def.appMode){
                                toastAlert(context, "Modo guardado! Será aplicado na próxima vez que abrir a aplicação", duration = Toast.LENGTH_LONG)
                            }
                        }
                    }

                    // appearencia ==============>
                    val (newMode, newThemeColor) = AppearanceWidget(mode, themeColor)
                    mode = newMode; themeColor = newThemeColor

                    // reminders -------->>
                    Button(
                        onClick = { navController.navigate("reminderspage") }
                    ) {
                        Text("Lembretes")
                    }

//                    Log.d("newvalues", "$themeColor")
                } else {
                    Text("Carregando dados")
                }

            }
        }
    ) {


        if (defs.isNotEmpty()){
//            val def = defs.first()
//            val themeColor by remember { mutableStateOf(def.themeColor)}
//            val rThemeColor = stringToColor(themeColor)  // Real theme color

//            colorObject.mainColor = rThemeColor

            Scaffold(
                bottomBar = { BottomAppBarHome(navController, "home", lerp(colorObject.mainColor, Color.White, 0.35f)) }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
//                        .background(color = HomeColor)
                        .background(color = Color.Transparent)
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
//                            color = HomeColor,
                            color = Color.Transparent,
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
                                .fillMaxWidth(0.75f)
                                .height(58.dp),
                            label = "Pesquisar Cântico / Oração",
                            maxLines = 1,
                            inputColor = colorObject.inputColor
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

                    // Madrinha dela: 84 955 7859

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



        } else {
            LoadingScreen()
        }


    }
}
