package com.samuel.oremoschangana.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.*

import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschangana.ui.theme.Purple40
import com.samuel.oremoschangana.ui.theme.PurpleGrey40
import com.samuel.oremoschangana.ui.theme.White
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samuel.oremoschangana.components.BottomAppBarPrincipal
import com.samuel.oremoschangana.ui.theme.Shapes
import com.samuel.oremoschangana.components.InputPesquisa as InputPesquisa1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracoesPage(navController: NavController){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Oracoes", color = MaterialTheme.colorScheme.primary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={
                            navController.popBackStack()
                        }
                    ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }

            )
        },
        bottomBar = {
            BottomAppBarPrincipal(navController)
        }
    ){paddingVales ->

        var pesquisaTexto by remember {
            mutableStateOf("")
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingVales),
        ){
            InputPesquisa1(
                value = pesquisaTexto,
                onValueChange = { pesquisaTexto = it },
                modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp, 20.dp, 10.dp),
                label = "Pesquisar oracao",
                maxLines = 1
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
//                    .padding(paddingVales),
                contentPadding = PaddingValues(10.dp)
            ){

                items(20){
                    ListItem(
                        modifier = Modifier.padding(2.dp, 0.dp, 2.dp, 7.dp),
                        headlineContent = { Text(text = "Item $it")},
                        leadingContent = {
                            Icon(imageVector = Icons.Default.Face, contentDescription = null)
                        },
                        shadowElevation = 6.dp,
                        colors = ListItemDefaults.colors(containerColor = Purple40),
                    )
                }
            }
        }


    }
}

