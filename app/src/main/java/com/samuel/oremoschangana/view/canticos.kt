package com.samuel.oremoschangana.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschangana.apresentacaoOracao.CancaoState
import com.samuel.oremoschangana.components.BottomAppBarPrincipal
import com.samuel.oremoschangana.components.InputPesquisa
import com.samuel.oremoschangana.dataOracao.Cancao
import com.samuel.oremoschangana.ui.theme.Orange
import com.samuel.oremoschangana.ui.theme.White
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanticosPage(state: CancaoState, navController: NavController, value: String){

    var pesquisaTexto by remember { mutableStateOf("") }


    val dados = if(value == "todos"){
        state.cancoes
    } else {
        state.cancoes.filter{ it.grupo == value }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Canticos", color = MaterialTheme.colorScheme.primary)},
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
                            .height(52.dp),
                        label = "Pesquisar cantico",
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
//            Text(text = "$value")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(
                    if (pesquisaTexto.isNotBlank()) {
                        dados.filter {
                            it.titulo.contains(pesquisaTexto, ignoreCase = true)
                        }
                    } else {
                        dados
                    }
                ) { cancao ->
                    CancaoItem(cancao = cancao)
                }

            }
        }
    }
}


@Composable
fun CancaoItem( cancao: Cancao) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(60.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize().weight(0.9f)
                .fillMaxHeight()
        ){
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cancao.titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

//            Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = cancao.subTitulo,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
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
            if (cancao.favorito){
                Icon(imageVector = Icons.Default.Star, contentDescription = "É favorito", tint = Orange)
            }else{
                Icon(imageVector = Icons.Outlined.Star, contentDescription = "Não é favorito", tint = White)
            }
        }

    }
}