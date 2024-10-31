package com.samuel.oremoschanganapt

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.ui.theme.OremosChanganaTheme
import com.samuel.oremoschanganapt.view.Apendice
import com.samuel.oremoschanganapt.view.CanticosAgrupados
import com.samuel.oremoschanganapt.view.CanticosPage
import com.samuel.oremoschanganapt.view.EachCantico
import com.samuel.oremoschanganapt.view.EachOracao
//import com.samuel.oremoschanganapt.view.CanticosAgrupados
//import com.samuel.oremoschanganapt.view.CanticosPage
//import com.samuel.oremoschanganapt.view.EachCantico
//import com.samuel.oremoschanganapt.view.EachOracao
import com.samuel.oremoschanganapt.view.FavoritosPage
import com.samuel.oremoschanganapt.view.FestasMoveis
import com.samuel.oremoschanganapt.view.Home
import com.samuel.oremoschanganapt.view.Licionario
import com.samuel.oremoschanganapt.view.MorePages
import com.samuel.oremoschanganapt.view.OracoesPage
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
//import com.samuel.oremoschanganapt.view.OracoesPage
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import java.util.Locale


class  MainActivity : ComponentActivity() {

    private val songViewModel: SongViewModel by viewModels()
    private val prayViewModel: PrayViewModel by viewModels()
    private val defViewModel: DefViewModel by viewModels()
    private val reminderViewModel: ReminderViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val allSongs by songViewModel.songs.collectAsState()
            val allPrays by prayViewModel.prays.collectAsState()
            val defs by defViewModel.defs.collectAsState()

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

                if(allPrays.isNotEmpty()) {
                    OremosChanganaTheme(darkTheme = mutableAppMode) {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {

                            val navController = rememberNavController()

                            NavHost(navController = navController, startDestination = "oracoespage") {
                                // define rotas

                                //                        composable("splash") {
                                //                            SplashWindow(navController)
                                //                        }

                                composable(route = "home") {
                                    Home(
                                        navController, songViewModel, prayViewModel, defViewModel,
                                        reminderViewModel
                                    )
                                }

                                composable(route = "oracoespage") {
                                    OracoesPage(
                                        navController, prayViewModel
                                    )
                                }
                                composable(route = "canticospage/{value}") { backStackEntry ->
                                    val value = backStackEntry.arguments?.getString("value") ?: ""
                                    CanticosPage(
                                        navController, value, songViewModel,
                                    )
                                }
                                //
                                composable(route = "eachCantico/{songid}") { aC ->
                                    val songid = aC.arguments?.getString("songid") ?: ""
                                    val songId = songid.toInt()
                                    EachCantico(navController, songId, songViewModel, defViewModel)
                                }

                                composable(route = "eachOracao/{prayid}") { eO ->
                                    val prayid = eO.arguments?.getString("prayid") ?: ""
                                    val prayId = prayid.toInt()
                                    EachOracao(navController, prayId, prayViewModel, defViewModel, reminderViewModel)
                                }
//                            //
                                composable(route = "canticosAgrupados") {
                                    CanticosAgrupados(
                                        navController, songViewModel
                                    )
                                }

                                composable(route = "favoritospage") {
                                    FavoritosPage(
                                        navController,
                                        prayViewModel, songViewModel
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
                            }
                        }
                    }
                }
            } else {
                Text("Carregando..")
            }
        }
    }
}