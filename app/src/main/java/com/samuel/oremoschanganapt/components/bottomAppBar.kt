package com.samuel.oremoschanganapt.components

import IconTextButton
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.ui.theme.DarkSecondary
import com.samuel.oremoschanganapt.ui.theme.LightSecondary

@Composable
fun SidebarNav(navController: NavController, activePage: String, modifier: Modifier = Modifier) {
    NavigationRail(
        modifier = if (activePage == "home") modifier else Modifier.padding(0.dp).width(80.dp),
        containerColor = Color.Transparent,
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(10.dp, 0.dp, 10.dp, 7.dp )
                .background( MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(15.dp) ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextButton("Home", "Ínicio", activePage == "home") { navController.navigate("home") }
            IconTextButton("Oracao", "Orações", activePage == "oracoespage") { navController.navigate("oracoespage") }
            IconTextButton("Cantico", "Cânticos", activePage == "canticosAgrupados") { navController.navigate("canticosAgrupados") }
            IconTextButton("MorePages", "Mais", activePage == "morepages") { navController.navigate("morepages") }
        }
    }
}


@Composable
fun BottomAppBarPrincipal(navController: NavController, activePage: String) {
    BottomAppBar(
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(75.dp).fillMaxWidth()
    ) {
        val bottomBgColor = if (isSystemInDarkTheme()) LightSecondary else DarkSecondary
//        Row(modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp, 0.dp, 10.dp, 7.dp)
//            .background(bottomBgColor,
//            shape = RoundedCornerShape(15.dp)),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            IconTextButton("Home", "Ínicio", activePage == "home") { navController.navigate("home") }
//            IconTextButton("Oracao", "Orações", activePage == "oracoespage") { navController.navigate("oracoespage") }
//            IconTextButton("Cantico", "Cânticos", activePage == "canticosAgrupados") { navController.navigate("canticosAgrupados") }
//            IconTextButton("MorePages", "Mais", activePage == "morepages") { navController.navigate("morepages") }
//        }

        Card(
            modifier = Modifier
                .fillMaxSize().height(60.dp)
                .padding(10.dp, 0.dp, 10.dp, 7.dp)
                .background(bottomBgColor,
                    shape = RoundedCornerShape(15.dp)),
            elevation = CardDefaults.elevatedCardElevation(3.dp)
        ) {
                    Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconTextButton("Home", "Ínicio", activePage == "home") { navController.navigate("home") }
            IconTextButton("Oracao", "Orações", activePage == "oracoespage") { navController.navigate("oracoespage") }
            IconTextButton("Cantico", "Cânticos", activePage == "canticosAgrupados") { navController.navigate("canticosAgrupados") }
            IconTextButton("MorePages", "Mais", activePage == "morepages") { navController.navigate("morepages") }
        }
        }
    }
}
