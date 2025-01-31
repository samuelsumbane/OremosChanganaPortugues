@file:OptIn(ExperimentalPermissionsApi::class)
package com.samuel.oremoschanganapt

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
//import com.google.android.play.core.review.ReviewManagerFactory
//import com.google.android.play.core.review.model.ReviewErrorCode
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.stringToColor
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.repository.TablesViewModels
import com.samuel.oremoschanganapt.ui.theme.OremosChanganaTheme
import com.samuel.oremoschanganapt.view.morepagesPackage.Apendice
//import com.samuel.oremoschanganapt.view.morepagesPackage.LovedDataPage
import com.samuel.oremoschanganapt.view.songsPackage.CanticosAgrupados
import com.samuel.oremoschanganapt.view.songsPackage.SongsPage
import com.samuel.oremoschanganapt.view.songsPackage.EachCantico
import com.samuel.oremoschanganapt.view.praysPackage.EachOracao
import com.samuel.oremoschanganapt.view.morepagesPackage.FestasMoveis
import com.samuel.oremoschanganapt.view.Home
import com.samuel.oremoschanganapt.view.morepagesPackage.Licionario
import com.samuel.oremoschanganapt.view.morepagesPackage.MorePages
import com.samuel.oremoschanganapt.view.morepagesPackage.LovedDataPage
import com.samuel.oremoschanganapt.view.praysPackage.OracoesPage
import com.samuel.oremoschanganapt.view.morepagesPackage.remindersPages.ConfigureReminder
import com.samuel.oremoschanganapt.view.morepagesPackage.remindersPages.RemindersPage
import com.samuel.oremoschanganapt.view.sideBar.About
import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuel.oremoschanganapt.functionsKotlin.generateTimestamp
import com.samuel.oremoschanganapt.functionsKotlin.scheduleNotificationForSongOrPray
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
//import com.samuel.oremoschanganapt.view.OracoesPage
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel


class  MainActivity : ComponentActivity() {

    private val songViewModel: SongViewModel by viewModels()
    private val prayViewModel: PrayViewModel by viewModels()
    private val defViewModel: DefViewModel by viewModels()
    private val reminderViewModel: ReminderViewModel by viewModels()
    private val commonViewModel: CommonViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            val viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)

//            val sucesso = viewModel.exportarFavoritos(context)
            val allSongs by songViewModel.songs.collectAsState()
            val allPrays by prayViewModel.prays.collectAsState()
            val defs by defViewModel.defs.collectAsState()
//            val lovedData by commonViewModel.lovedData.collectAsState()


            TablesViewModels.songViewModel = songViewModel
            TablesViewModels.prayViewModel = prayViewModel
            TablesViewModels.commonViewModel = commonViewModel

//            updateLocale(this, Locale(settings.language))
////                val mode by remember { mutableStateOf(settings.mode) }
            if (defs.isNotEmpty()){
                val def = defs.first()

                val appMode = when (def.appMode) {
                    "Dark" -> true
                    "Light" -> false
                    else -> isSystemInDarkTheme()
                }
                val mutableAppMode by remember { mutableStateOf(appMode) }

                colorObject.mainColor = stringToColor(def.themeColor)
                val rThemeColor = colorObject.mainColor

                val localContext = LocalContext.current

                if (allPrays.isNotEmpty()) {
                    OremosChanganaTheme(darkTheme = mutableAppMode) {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                val postNotificationPermission = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
                                LaunchedEffect(key1 = true) {
                                    if (!postNotificationPermission.status.isGranted) {
                                        postNotificationPermission.launchPermissionRequest()
                                    }
                                }
                            }

                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                                val writeStorage = rememberPermissionState(permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                LaunchedEffect(key1 = true) {
                                    if (!writeStorage.status.isGranted) {
                                        writeStorage.launchPermissionRequest()
                                    }
                                }
                            }


//                            val lastTimestamp = generateTimestamp("2025-01-31", "21:15")
//                            Log.d("hora", "$lastTimestamp")

//                            val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager


//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                                if (alarmManager.canScheduleExactAlarms()) {
//                                    if (lastTimestamp != null) {
//                                        scheduleNotificationForSongOrPray(this, lastTimestamp)
//                                        scheduleNotificationForSongOrPray(this, lastTimestamp)
////                                        Log.d("future", "$lastTimestamp")
//                                    }
//                                } else {
//                                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
//                                    this.startActivity(intent)
//                                }
//                            } else {
//                                if (lastTimestamp != null) {
//                                    scheduleNotificationForSongOrPray(this, lastTimestamp)
//                                    Log.d("future", "$lastTimestamp")
//                                }
//                            }


                            val navController = rememberNavController()
                            NavHost(navController = navController, startDestination = "home") {
                                // define rotas
                                //     composable("splash") {
                                //          SplashWindow(navController)
                                //     }

                                composable(route = "home") {
                                    Home(
                                        navController, songViewModel, prayViewModel, defViewModel,
                                        reminderViewModel
                                    )
                                }

                                composable(route = "oracoespage") {
                                    OracoesPage(
                                        navController, prayViewModel, commonViewModel
                                    )
                                }

                                composable(route = "canticospage/{value}/{readbleValue}") { backStackEntry ->
                                    val value = backStackEntry.arguments?.getString("value") ?: ""
                                    val readbleValue = backStackEntry.arguments?.getString("readbleValue") ?: ""

                                    SongsPage(
                                        navController, value, readbleValue, songViewModel, commonViewModel
                                    )
                                }
                                //
                                composable(route = "eachCantico/{songid}") { aC ->
                                    val songid = aC.arguments?.getString("songid") ?: ""
                                    val songId = songid.toInt()
                                    EachCantico(navController, songId, songViewModel, reminderViewModel, defViewModel)
                                }

                                composable(route = "eachOracao/{prayid}") { eO ->
                                    val prayid = eO.arguments?.getString("prayid") ?: ""
                                    val prayId = prayid.toInt()
                                    EachOracao(navController, prayId, prayViewModel, defViewModel, reminderViewModel, commonViewModel)
                                }
//                            //
                                composable(route = "canticosAgrupados") {
                                    CanticosAgrupados(
                                        navController, songViewModel
                                    )
                                }

                                composable(route = "favoritospage") {
                                    LovedDataPage(
                                        navController,
                                        prayViewModel,
                                        songViewModel
                                    )
                                }

                                composable(route = "apendice") {
                                    Apendice(navController)
                                }

                                composable(route = "festasmoveis") {
                                    FestasMoveis(navController)
                                }

                                composable(route = "licionario") {
                                    Licionario(navController)
                                }

                                composable(route = "morepages") {
                                    MorePages(navController)
                                }

                                composable("reminderspage") {
                                    RemindersPage(
                                        navController,
                                        songViewModel,
                                        prayViewModel,
                                        reminderViewModel
                                    )
                                }

                                composable("about") {
                                    About(navController)
                                }

                                composable("configurereminder/{id}/{table}/{rdate}/{rtime}/{reminderid}"){ cR ->
                                    val stringId = cR.arguments?.getString("id") ?: ""
                                    val id = stringId.toInt()
                                    val table = cR.arguments?.getString("table") ?: ""
                                    val rdate = cR.arguments?.getString("rdate") ?: ""
                                    val rDate = rdate.toLong()
                                    val rtime = cR.arguments?.getString("rtime") ?: ""
                                    val rTime = rtime.toLong()
                                    val rid = cR.arguments?.getString("reminderid") ?: ""
                                    val rId = rid.toInt()
                                    ConfigureReminder(navController, id, table, rDate, rTime, rId, reminderViewModel)
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
}
