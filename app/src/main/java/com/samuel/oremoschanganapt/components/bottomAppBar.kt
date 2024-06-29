package com.samuel.oremoschanganapt.components

import IconTextButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun BottomAppBarPrincipal(navController: NavController, activePage: String){
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
        ){

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp, 0.dp, 2.dp, 4.dp )
                .background( MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(15.dp) ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextButton("Home", "Ínicio", activePage == "home") { navController.navigate("home") }
            IconTextButton("Oracao", "Orações", activePage == "oracoespage") { navController.navigate("oracoespage") }
            IconTextButton("Cantico", "Cânticos", activePage == "canticosAgrupados") { navController.navigate("canticosAgrupados") }
            IconTextButton("Favoritos", "Favoritos", activePage == "favoritospage") { navController.navigate("favoritospage") }
        }
    }
}