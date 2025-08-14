package com.samuel.oremoschanganapt.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.SetIdPreference
import com.samuel.oremoschanganapt.components.*
//import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuel.oremoschanganapt.db.data.songsData
//import com.samuel.oremoschanganapt.functionsKotlin.AdvancedColorPickerDrawScope
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.getIdSet
//import com.samuel.oremoschanganapt.getInitialThemeColor
import com.samuel.oremoschanganapt.getInitialThemeMode
import com.samuel.oremoschanganapt.getThemeColor
//import com.samuel.oremoschanganapt.repository.TablesViewModels
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.repository.Configs
import com.samuel.oremoschanganapt.view.sideBar.AppearanceWidget
//import com.samuel.oremoschanganapt.view.sideBar.RowBackup
import com.samuel.oremoschanganapt.view.sideBar.RowAbout
import com.samuel.oremoschanganapt.db.data.praysData
import com.samuel.oremoschanganapt.view.sideBar.PreferencesWidget
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun Home(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textInputValue by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var themeColor = ColorObject.mainColor

    var mode by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val allPrays = praysData

    val filteredPrays = remember(allPrays, textInputValue){
        if (textInputValue.isNotEmpty()) {
            allPrays.filter { it.title.contains(textInputValue, ignoreCase = true)}
        } else emptyList()
    }

    val filteredSongs = remember(songsData, textInputValue){
        if (textInputValue.isNotBlank()) {
            val numOrNot = isNumber(textInputValue)
            if (numOrNot) {
                songsData.filter { it.number == textInputValue }
            } else {
                songsData.filter {
                    it.title.contains(textInputValue, ignoreCase = true)
                }
            }
        } else emptyList()
    }

    var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(Unit) {
        lovedSongsIds = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
        mode = getInitialThemeMode(context)
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
                Modifier
                    .padding(end = 10.dp, top = 24.dp)
                    .statusBarsPadding()
                    .width(if (isPortrait) inVertical.dp else inHorizontal.dp )
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Column (
                    Modifier.fillMaxWidth(0.95f)
                        .padding(start = 12.dp)
                        .fillMaxHeight()
                        .verticalScroll(scrollState),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.configurations).uppercase(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)

                        IconButton(
                            onClick = { scope.launch { drawerState.close() } },
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close sidebar",
                                tint = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Spacer(Modifier.height(75.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
                            // appearance ------->>
                            AppearanceWidget(navController, mode)
                            //
                            PreferencesWidget(navController)
                            // About --------->>
                            RowAbout(navController)
                        }
                    }

                }
            }
        }
    ) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row (Modifier.fillMaxWidth()
                            .padding(top = 35.dp)
                        ) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu",
                                    tint = Color.White, modifier = Modifier.size(30.dp))
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        InputSearch(value = textInputValue, onValueChange = { textInputValue = it },
                            placeholder = stringResource(R.string.search_song_or_pray),
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(45.dp)
//                                .background(color = searchBgColor, shape = RoundedCornerShape(35.dp)),
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
                            lazyColumn {
                                items (filteredPrays) { pray ->
                                    PrayRow(
                                        navController, pray = pray,
                                        showStarButton = false,
                                    )
                                }

                                items (filteredSongs) { song ->
                                    SongRow(
                                        navController, song = song,
                                        blackBackground = true,
                                        showStarButton = false
                                    )
                                }
                            }
                        }
                    }
                }
            }

//        } else {
//            LoadingScreen()
//        }
    }

}
