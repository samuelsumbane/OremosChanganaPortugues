package com.samuel.oremoschangana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samuel.oremoschangana.ui.theme.OremosChanganaTheme
import com.samuel.oremoschangana.view.CanticosPage
import com.samuel.oremoschangana.view.FavoritosPage
import com.samuel.oremoschangana.view.Home
import com.samuel.oremoschangana.view.OracoesPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OremosChanganaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination="home"){
                        // define rotas
                        composable(route = "home"){ Home(navController) }
                        composable(route = "oracoespage"){ OracoesPage(navController) }
                        composable(route = "canticospage"){ CanticosPage(navController) }
                        composable(route = "favoritospage"){ FavoritosPage(navController) }
                    }
                }
            }
        }
    }
}
