package com.samuel.oremoschanganapt.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.buttons.MorePagesBtn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorePages(navController: NavController){
    Scaffold(
       topBar = {
           TopAppBar(
               title = { },
               navigationIcon = {
                   IconButton( onClick = { navController.popBackStack() } ) {
                      Icon(Icons.Default.ArrowBack, contentDescription = "back")
                   }
               }
           )
       }
    ) { innerPadding ->
        Column(
            Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.fillMaxWidth(0.77f)
//            .height()
//            .background
            //           (Color.Green)
            ){
                val cS = 20 // cornerShape
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    // two pages.
                    MorePagesBtn("Apendice", RoundedCornerShape(cS,  cS,  0, cS)){
                        navController.navigate("apendice")
                    }

                    MorePagesBtn("Festas Moveis", RoundedCornerShape(cS, cS, cS, 0)){
                        navController.navigate("festasmoveis")
                    }
                }
                Spacer(Modifier.height(20.dp))

                Row(
                    Modifier.fillMaxWidth(),
//                .background(Color.Red),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    MorePagesBtn("Favoritos", RoundedCornerShape(cS, 0, cS, cS)) {

                    }

                    MorePagesBtn("Licionario", RoundedCornerShape(0, cS, cS, cS)) {
                        navController.navigate("licionario")
                    }
                }
            }
        }

        ShortcutsButton(navController)

    }
}

