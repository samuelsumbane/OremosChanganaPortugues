package com.samuel.oremoschanganapt.view

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.*
import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.functionsKotlin.stringToColor
import com.samuel.oremoschanganapt.repository.TablesViewModels
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.view.sideBar.AppearanceWidget
import com.samuel.oremoschanganapt.view.sideBar.RowBackup
import com.samuel.oremoschanganapt.view.sideBar.RowAbout
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun Home( navController: NavController, songViewModel: SongViewModel,
          prayViewModel: PrayViewModel, defViewModel: DefViewModel,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textInputValue by remember { mutableStateOf("") }
    val allSongs by songViewModel.songs.collectAsState()
    val allPrays by prayViewModel.prays.collectAsState()
    val defs by defViewModel.defs.collectAsState()
    var showModal by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val commonViewModel = TablesViewModels.commonViewModel!!

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val filteredPrays = remember(allPrays, textInputValue){
        if (textInputValue.isNotEmpty()) {
            allPrays.filter { it.title.contains(textInputValue, ignoreCase = true)}
        } else emptyList()
    }

    val filteredSongs = remember(allSongs, textInputValue){
        if (textInputValue.isNotBlank()) {
            val numOrNot = isNumber(textInputValue)
            if (numOrNot) {
                allSongs.filter { it.number == textInputValue }
            } else {
                allSongs.filter {
                    it.title.contains(textInputValue, ignoreCase = true)
                }
            }
        } else emptyList()
    }

    LaunchedEffect(textInputValue) {
        showModal = textInputValue != ""
    }

    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val inVertical by remember(screenWidth) {
        derivedStateOf { screenWidth - (screenWidth * 0.15) }
    }
    val inHorizontal by remember(screenHeight) {
        derivedStateOf { screenHeight }
    }

    var iconColorState by remember { mutableStateOf("Keep")}

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                Modifier.width(if (isPortrait) inVertical.dp else inHorizontal.dp )
                    .padding(end = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Column (
                    Modifier.fillMaxWidth(0.95f)
                        .fillMaxHeight()
                        .padding(start=10.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Text("Configurações".uppercase(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
                    Spacer(Modifier.height(75.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
                        if (defs.isNotEmpty()) {
                            val def = defs.first()
                            var themeColor by remember { mutableStateOf(def.themeColor)}
                            var mode by remember { mutableStateOf(def.appMode) }

                            LaunchedEffect(themeColor) {
                                coroutineScope {
                                    if (def.themeColor != themeColor){
                                        defViewModel.updateDef("themeColor", themeColor)
                                        colorObject.mainColor = stringToColor(themeColor)
                                        iconColorState = "Reload"
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
                            // appearencia ------->>
                            val (newMode, newThemeColor) = AppearanceWidget(mode, themeColor)
                            mode = newMode; themeColor = newThemeColor

                        } else LoadingScreen()

                        RowBackup(commonViewModel = CommonViewModel())

                        // About --------->>
                        RowAbout(navController)
                    }
                }
            }
        }
    ) {
        if (defs.isNotEmpty()){
            Scaffold(
                bottomBar = {
                    if (isPortrait) BottomAppBarPrincipal(navController, "home", iconColorState)
                }
            ) {
                Box(Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.homepic),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent)))
                    )

                    if (!isPortrait) SidebarNav(navController, "home", modifier = Modifier.fillMaxHeight().width(80.dp).padding(top=30.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row (Modifier.fillMaxWidth()) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu",
                                    tint = Color.White, modifier = Modifier.size(30.dp))
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        val searchBgColor = if (isSystemInDarkTheme()) Color(0xFF4A4F50) else Color(0xFF9DA0A1)

                        InputSearch(value = textInputValue, onValueChange = { textInputValue = it },
                            placeholder = "Pesquisar Cântico / Oração",
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(45.dp)
                                .background(color = searchBgColor, shape = RoundedCornerShape(35.dp)),
                        )

                        Spacer(Modifier.height(20.dp))
                        Column {
                            HomeTexts(text = "Oremos", fontSize = 45)
                            Spacer(modifier = Modifier.height(9.dp))
                            HomeTexts(text = "A HI KHONGELENI", fontSize = 23)
                        }
                    }

                    if (showModal) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.90f)
                                .heightIn(min = 90.dp, max = 500.dp)
                                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(15.dp))
                                .align(Alignment.CenterEnd),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items (filteredPrays) { pray ->
                                    PrayRow(navController, prayViewModel, pray)
                                }

                                items (filteredSongs) { song ->
                                    SongRow(navController, songViewModel, song)
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
