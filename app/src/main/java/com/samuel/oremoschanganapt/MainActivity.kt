@file:OptIn(ExperimentalPermissionsApi::class)
package com.samuel.oremoschanganapt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.functionsKotlin.DataCollection
import com.samuel.oremoschanganapt.functionsKotlin.updateLocale
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.repository.Configs
import com.samuel.oremoschanganapt.repository.Configs.appLocale
import com.samuel.oremoschanganapt.ui.theme.OremosChanganaTheme
//import com.samuel.oremoschanganapt.view.DataCollection
import com.samuel.oremoschanganapt.view.Home
import com.samuel.oremoschanganapt.view.SplashWindow
import com.samuel.oremoschanganapt.view.eachPage
import com.samuel.oremoschanganapt.view.morepagesPackage.Apendice
import com.samuel.oremoschanganapt.view.morepagesPackage.FestasMoveis
import com.samuel.oremoschanganapt.view.morepagesPackage.Licionario
import com.samuel.oremoschanganapt.view.morepagesPackage.LovedDataPage
import com.samuel.oremoschanganapt.view.morepagesPackage.MorePages
import com.samuel.oremoschanganapt.view.praysPackage.OracoesPage
import com.samuel.oremoschanganapt.view.settingsPackage.AppearancePage
import com.samuel.oremoschanganapt.view.sideBar.About
import com.samuel.oremoschanganapt.view.songsPackage.CanticosAgrupados
import com.samuel.oremoschanganapt.view.songsPackage.SongsPage
import com.samuel.oremoschanganapt.db.data.praysData
import com.samuel.oremoschanganapt.view.sideBar.newColorPage
import java.util.Locale


class  MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
//            val reminders by reminderViewModel.reminders.collectAsState()
            // New
            var fontSize by remember { mutableStateOf("") }
            var themeMode by remember { mutableStateOf("") }
            var themeColor by remember { mutableStateOf(Color.Unspecified) }
            var secondThemeColor by remember { mutableStateOf(Color.Unspecified) }
//            val themeColor by getThemeColor(context).collectAsState(initial = Color.Unspecified)
            var initialLanguage by remember { mutableStateOf("") }


            LaunchedEffect(Unit) {
                fontSize = getInitialFontSize(context)
                themeMode = getInitialThemeMode(context)
                themeColor = getInitialThemeColor(context)
                secondThemeColor = getInitialSecondThemeColor(context)
                initialLanguage = getInitialLanguage(context)

                updateLocale(context, locale = Locale(if (initialLanguage == "404") "pt" else initialLanguage))
                appLocale = initialLanguage



                Configs.fontSize = fontSize
            }

            val appMode = when (themeMode) {
                "Dark" -> true
                "Light" -> false
                else -> isSystemInDarkTheme()
            }

            if (themeColor != Color.Unspecified || initialLanguage == "404") {
                ColorObject.mainColor = themeColor
                ColorObject.secondColor =
                    if (secondThemeColor == Color.Unspecified || secondThemeColor == Color.Transparent) themeColor else secondThemeColor

                if (praysData.isNotEmpty()) {
                    OremosChanganaTheme(darkTheme = appMode) {
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

                                LaunchedEffect(key1 = writeStorage.status.isGranted) {
                                    if (!writeStorage.status.isGranted) {
                                        writeStorage.launchPermissionRequest()
                                    }
                                }

                                val readStorage = rememberPermissionState(permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                LaunchedEffect(key1 = readStorage.status.isGranted) {
                                    if (!readStorage.status.isGranted) {
                                        readStorage.launchPermissionRequest()
                                    }
                                }
                            }

                                val navController = rememberNavController()
//                              NavHost(navController = navController, startDestination = "splash") {
                                NavHost(navController = navController, startDestination = "home") {
//                                 define routes ------->>

                                    composable("splash") {
                                        SplashWindow(navController)
                                    }

                                    composable(route = "home") {
                                        Home(navController)
                                    }

                                    composable(route = "appearancePage") {
                                        AppearancePage(navController)
                                    }

                                    composable(route = "newColorPage") {
                                        newColorPage(navController)
                                    }

                                    composable(route = "oracoespage") {
                                        OracoesPage(navController)
                                    }

                                    composable(route = "canticospage/{value}/{readbleValue}") { backStackEntry ->
                                        val value = backStackEntry.arguments?.getString("value") ?: ""
                                        val readbleValue = backStackEntry.arguments?.getString("readbleValue") ?: ""
                                        SongsPage(navController, value, readbleValue)
                                    }

                                    composable(route = "eachCantico/{songId}") { aC ->
                                        val stringSongId = aC.arguments?.getString("songId") ?: "0"
                                        val songId = stringSongId.toInt()
                                        eachPage(
                                            navController,
                                            dataCollection = DataCollection.SONGS,
                                            itemId = songId
                                        )
                                    }

                                    composable(route = "eachOracao/{prayid}") { eO ->
                                        val prayid = eO.arguments?.getString("prayid") ?: "0"
                                        val prayId = prayid.toInt()
                                        eachPage(
                                            navController,
                                            dataCollection = DataCollection.PRAYS,
                                            itemId = prayId
                                        )
                                    }

                                    composable(route = "canticosAgrupados") {
                                        CanticosAgrupados(navController)
                                    }

                                    composable(route = "favoritospage") {
                                        LovedDataPage(navController)
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

//                                composable("reminderspage") {
//                                    RemindersPage(
//                                        navController,
//                                        songViewModel,
//                                        prayViewModel,
//                                        reminderViewModel
//                                    )
//                                }

                                    composable("about") {
                                        About(navController)
                                    }

                                    composable("configurereminder/{id}/{table}/{rdatetime}/{reminderid}"){ cR ->
                                        val stringId = cR.arguments?.getString("id") ?: ""
                                        val id = stringId.toInt()
                                        val table = cR.arguments?.getString("table") ?: ""
                                        val rdatetime = cR.arguments?.getString("rdatetime") ?: ""
                                        val rDateTime = rdatetime.toLong()
                                        val rid = cR.arguments?.getString("reminderid") ?: ""
                                        val rId = rid.toInt()

//                                    ConfigureReminder(navController, id, table, rDateTime, rId, reminderViewModel)

                                    }
                                }
                        }
                    }
                }
//            } else {
//                LoadingScreen()
//            }
                } else LoadingScreen()

        }
    }
}
