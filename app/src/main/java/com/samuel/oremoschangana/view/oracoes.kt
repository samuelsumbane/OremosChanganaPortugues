package com.samuel.oremoschangana.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.*

import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samuel.oremoschangana.components.BottomAppBarPrincipal
import com.samuel.oremoschangana.components.InputPesquisa
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.samuel.oremoschangana.apresentacaoOracao.OracaoState
import com.samuel.oremoschangana.dataOracao.Oracao
import com.samuel.oremoschangana.ui.theme.Orange
import com.samuel.oremoschangana.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracoesPage(state: OracaoState, navController: NavController){
    var pesquisaTexto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Orações", color = MaterialTheme.colorScheme.primary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    InputPesquisa(
                        value = pesquisaTexto,
                        onValueChange = { pesquisaTexto = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp, 0.dp, 20.dp, 10.dp)
                            .height(58.dp),
                        label = "Pesquisar oração",
                        maxLines = 1
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBarPrincipal(navController)
        }
    ){paddingVales ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingVales),
        ){

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(
                    if (pesquisaTexto.isNotBlank()) {
                        state.oracoes.filter {
                            it.titulo.contains(pesquisaTexto, ignoreCase = true)
                        }
                    } else {
                        state.oracoes
                    }
                ) { oracao ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .clickable{
                                navController.navigate("eachOracao/${oracao.titulo}/${oracao.corpo}")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize().weight(0.9f)
                                .fillMaxHeight()
                        ){
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = oracao.titulo,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = White,
                                    textAlign = TextAlign.Center
                                )

//            Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = oracao.subTitulo,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxSize().weight(0.1f)
                                .height(60.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            if (oracao.favorito){
                                Icon(imageVector = Icons.Default.Star, contentDescription = "É favorito", tint = Orange)
                            }else{
                                Icon(imageVector = Icons.Outlined.Star, contentDescription = "Não é favorito", tint = White)
                            }
                        }

                    }
                }
            }
        }
    }
}


//@Composable
//fun OracaoItem( oracao: Oracao ) {
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .height(60.dp)
//            .clip(RoundedCornerShape(14.dp))
//            .background(MaterialTheme.colorScheme.primaryContainer)
//            .padding(8.dp, 0.dp, 0.dp, 0.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize().weight(0.9f)
//               .fillMaxHeight()
//        ){
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = oracao.titulo,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//
////            Spacer(modifier = Modifier.height(2.dp))
//
//            Text(
//                text = oracao.subTitulo,
//                fontSize = 16.sp,
//                color = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//        }
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxSize().weight(0.1f)
//                .height(60.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ){
//            if (oracao.favorito){
//                Icon(imageVector = Icons.Default.Star, contentDescription = "É favorito", tint = Orange)
//            }else{
//                Icon(imageVector = Icons.Outlined.Star, contentDescription = "Não é favorito", tint = White)
//            }
//        }
//
//    }
//}

