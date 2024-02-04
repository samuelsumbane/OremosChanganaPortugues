package com.samuel.oremoschangana.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import IconTextButton
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschangana.ui.theme.HomeColor
@Composable
fun BottomAppBarPrincipal(navController: NavController){
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp)
        ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp, 0.dp, 2.dp, 4.dp )
                .background( HomeColor, shape = RoundedCornerShape(15.dp) ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextButton("Home", "Ínicio") { navController.navigate("home") }
            IconTextButton("Oracao", "Orações") { navController.navigate("oracoespage") }
            IconTextButton("Cantico", "Cantico") { navController.navigate("canticospage") }
            IconTextButton("Favoritos", "Favoritos") { navController.navigate("favoritospage") }
        }
    }
}