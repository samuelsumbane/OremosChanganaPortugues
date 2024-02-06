package com.samuel.oremoschangana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.samuel.oremoschangana.apresentacaoOracao.CancoesViewModel
import com.samuel.oremoschangana.apresentacaoOracao.OracoesViewModel
import com.samuel.oremoschangana.dataOracao.Oracao
import com.samuel.oremoschangana.dataOracao.OracoesDatabase
import com.samuel.oremoschangana.dataOracao.CancoesDatabase
import com.samuel.oremoschangana.ui.theme.OremosChanganaTheme
import com.samuel.oremoschangana.view.CanticosAgrupados
import com.samuel.oremoschangana.view.CanticosPage
import com.samuel.oremoschangana.view.FavoritosPage
import com.samuel.oremoschangana.view.Home
import com.samuel.oremoschangana.view.OracoesPage


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
                    NavHost(navController = navController, startDestination="canticosAgrupados"){
                        // define rotas
                        composable(route = "home"){ Home(navController) }
                        composable(route = "oracoespage"){ OracoesPage( state, navController) }
                        composable(route = "canticospage/{value}"){ backStackEntry ->
                            val value = backStackEntry.arguments?.getString("value") ?: ""
                            CanticosPage( cstate, navController, value)
                        }

                        composable(route = "canticosAgrupados"){ CanticosAgrupados( cstate, navController) }
                        composable(route = "favoritospage"){ FavoritosPage(navController) }
                    }
                }
            }
        }

    }

}
