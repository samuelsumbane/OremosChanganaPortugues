package com.samuel.oremoschanganapt.view

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
//import com.samuel.oremoschanganapt.NotificationWorker
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.*
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
//import com.samuel.oremoschanganapt.functionsKotlin.scheduleReminderCheck
import com.samuel.oremoschanganapt.functionsKotlin.stringToColor
import com.samuel.oremoschanganapt.repository.TablesViewModels
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.Dodgerblue
import com.samuel.oremoschanganapt.view.sideBar.AppearanceWidget
import com.samuel.oremoschanganapt.view.sideBar.RowBackup
import com.samuel.oremoschanganapt.view.sideBar.RowAbout
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
    val commonViewModel = TablesViewModels.commonViewModel!!
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

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
                    it.title.contains(textInputValue,ignoreCase = true)
                }
            }
        } else emptyList()
    }

    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val inVertical by remember(screenWidth) {
        derivedStateOf { screenWidth - (screenWidth * 0.15) }
    }
    val inHorizontal by remember(screenHeight) {
        derivedStateOf { screenHeight }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                Modifier.width(if (isPortrait) inVertical.dp else inHorizontal.dp )
                    .padding(end = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                var mainColor = colorObject.mainColor

                Column (
                    Modifier.fillMaxWidth(0.95f)
                        .fillMaxHeight()
                        .padding(start=10.dp)
                        .verticalScroll(scrollState),
//                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Text("Configurações", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(75.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                    ) {
                        if (defs.isNotEmpty()) {
                            val def = defs.first()
                            var themeColor by remember { mutableStateOf(def.themeColor)}
                            var mode by remember { mutableStateOf(def.appMode) }

                            LaunchedEffect(themeColor) {
                                coroutineScope {
                                    if (def.themeColor != themeColor){
                                        defViewModel.updateDef("themeColor", themeColor)
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
                            // appearencia ------->>
                            val (newMode, newThemeColor) = AppearanceWidget(mode, themeColor)
                            mode = newMode; themeColor = newThemeColor

                        } else LoadingScreen()

                        RowBackup(
                            onBackupClick = { commonViewModel.exportBackupToExternalStorage(context) },
                            onRestoreClick = { commonViewModel.restoreBackupFromExternalStorage(context) }
                        )

                        // About --------->>
                        RowAbout(navController)
                    }
                }
            }
        }
    ) {

        if (defs.isNotEmpty()){
            Scaffold(
                bottomBar = { if (isPortrait) BottomAppBarPrincipal(navController, "home") }
            ) {
                Box(Modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.homepic),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

//                    allR.forEach {
//                        Log.d("Reminder", "ex: ${it.reminderDate} || ${it.reminderTime}")
//                    }
//                    scheduleReminderCheck(context)

                    if (!isPortrait) {
                        SidebarNav(navController, "home", modifier = Modifier.fillMaxHeight().width(80.dp).padding(top=30.dp))
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight(.3f)
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

                        OutlinedTextField(
                            value = textInputValue,
                            onValueChange = {
                                textInputValue = it
                                showModal = textInputValue != ""
                            },
                            label = { Text(text = "Pesquisar Cântico / Oração", color = MaterialTheme.colorScheme.background, modifier = Modifier.background(tertiaryColor)) },
                            maxLines = 1,
                            shape = RoundedCornerShape(30.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor =  tertiaryColor,
                                unfocusedContainerColor = tertiaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.background),
                            modifier = Modifier.height(55.dp)
                        )

                        Column {
                            HomeTexts(text = "Oremos", fontSize = 45)
                            Spacer(modifier = Modifier.height(4.dp))
                            HomeTexts(text = "A HI KHONGELENI", fontSize = 30)
                        }
                    }

                    if (showModal) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.90f)
                                .heightIn(min = 90.dp, max = 500.dp)
                                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(15.dp))
                                .border(0.7.dp, Dodgerblue, RoundedCornerShape(15.dp))
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
                                items (filteredPrays, key={ it.prayId }) { pray ->
                                    PrayRow(navController, prayViewModel, pray)
                                }

                                items (filteredSongs, key={ it.songId }) { song ->
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
