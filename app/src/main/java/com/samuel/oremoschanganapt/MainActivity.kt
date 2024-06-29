package com.samuel.oremoschanganapt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.samuel.oremoschanganapt.apresentacaoOracao.CancoesViewModel
import com.samuel.oremoschanganapt.apresentacaoOracao.OracoesViewModel
import com.samuel.oremoschanganapt.dataOracao.CancoesDatabase
import com.samuel.oremoschanganapt.dataOracao.OracoesDatabase
import com.samuel.oremoschanganapt.ui.theme.OremosChanganaTheme
import com.samuel.oremoschanganapt.view.CanticosAgrupados
import com.samuel.oremoschanganapt.view.CanticosPage
import com.samuel.oremoschanganapt.view.EachCantico
import com.samuel.oremoschanganapt.view.EachOracao
import com.samuel.oremoschanganapt.view.FavoritosPage
import com.samuel.oremoschanganapt.view.Home
import com.samuel.oremoschanganapt.view.OracoesPage
import com.samuel.oremoschanganapt.view.SplashWindow


class MainActivity : ComponentActivity() {

    private val database by lazy{
        Room.databaseBuilder(
            applicationContext,
            OracoesDatabase::class.java,
            "oracoesCancoes1.db"
        ).build()
    }

    private val viewModel by viewModels<OracoesViewModel> (
        factoryProducer = {
            object: ViewModelProvider.Factory{
                override fun<T: ViewModel> create(modelClass: Class<T>): T{
                    return OracoesViewModel(database.dao) as T
                }
            }
        }
    )


    private val cancoesDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            CancoesDatabase::class.java,
            "cancoes.db"
        ).build()
    }

    private val cviewModel by viewModels<CancoesViewModel> (
        factoryProducer = {
            object: ViewModelProvider.Factory{
                override fun<T: ViewModel> create(modelClass: Class<T>): T{
                    return CancoesViewModel(cancoesDatabase.cdao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OremosChanganaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    val cstate by cviewModel.cstate.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination="splash"){
                        composable("splash") {
                            SplashWindow(navController)
                        }
                        // define rotas
                        composable(route = "home"){
                            Home(state, cstate, navController, onEvent = cviewModel::onEvent, onEventO = viewModel::onEvent)
                        }

                        composable(route = "oracoespage"){ OracoesPage( state, navController, onEvent = viewModel::onEvent) }
                        composable(route = "canticospage/{value}"){ backStackEntry ->
                            val value = backStackEntry.arguments?.getString("value") ?: ""
                            CanticosPage( cstate, navController, value, onEvent = cviewModel::onEvent)
                        }

                        composable(route = "eachCantico/{numero}/{titulo}/{corpo}"){ aC ->
                            val numero = aC.arguments?.getString("numero") ?: ""
                            val titulo = aC.arguments?.getString("titulo") ?: ""
//                            val subTitulo = aC.arguments?.getString("subTitulo") ?: ""
                            val corpo = aC.arguments?.getString("corpo") ?: ""
                            EachCantico(navController, numero, titulo, corpo)
                        }

                        composable(route = "eachOracao/{titulo}/{corpo}"){ eO ->
                            val titulo = eO.arguments?.getString("titulo") ?: ""
//                            val subTitulo = aC.arguments?.getString("subTitulo") ?: ""
                            val corpo = eO.arguments?.getString("corpo") ?: ""
                            EachOracao(navController, titulo, corpo)
                        }

                        composable(route = "canticosAgrupados"){ CanticosAgrupados( cstate, navController) }
                        composable(route = "favoritospage"){ FavoritosPage(state, cstate, navController, onEvent = cviewModel::onEvent, onEventO = viewModel::onEvent) }
                    }

                }
            }
        }

    }
}
