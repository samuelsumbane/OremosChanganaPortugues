package com.samuel.oremoschanganapt.components

import IconTextButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.repository.colorObject

@Composable
fun BottomAppBarPrincipal(navController: NavController, activePage: String){
    BottomAppBar(
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(75.dp)
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
        ){
        val mainColor by remember { mutableStateOf(colorObject.mainColor) }

        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextButton("Home", "Ínicio", activePage == "home", iconColor = mainColor) { navController.navigate("home") }
            IconTextButton("Oracao", "Orações", activePage == "oracoespage", iconColor = mainColor) { navController.navigate("oracoespage") }
            IconTextButton("Cantico", "Cânticos", activePage == "canticosAgrupados", iconColor = mainColor) { navController.navigate("canticosAgrupados") }
            IconTextButton("MorePages", "Mais", activePage == "morepages", iconColor = mainColor) { navController.navigate("morepages") }
        }
    }
}

@Composable
fun BottomAppBarHome(navController: NavController, activePage: String, activeIconColor: Color ){
    val menuColor = colorObject.mainColor
    BottomAppBar(
        containerColor = Color.Transparent,
//            .padding(0.dp, 0.dp, 0.dp, 5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp, 0.dp, 2.dp, 4.dp )
//                .background( MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(15.dp) ),
                .background( menuColor, shape = RoundedCornerShape(15.dp) ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // onPrimary
            val textIconColor = Color.White
            IconTextButton("Home", "Ínicio", activePage == "home", textColor = textIconColor, activeIconColor = activeIconColor) { navController.navigate("home") }
            IconTextButton("Oracao", "Orações", activePage == "oracoespage", textColor = textIconColor) { navController.navigate("oracoespage") }
            IconTextButton("Cantico", "Cânticos", activePage == "canticosAgrupados", textColor = textIconColor) { navController.navigate("canticosAgrupados") }
            IconTextButton("MorePages", "Mais", activePage == "morepages", textColor = textIconColor) { navController.navigate("morepages") }
//            IconTextButton("Favoritos", "Favoritos", activePage == "favoritospage") { navController.navigate("favoritospage") }
        }
    }
}
