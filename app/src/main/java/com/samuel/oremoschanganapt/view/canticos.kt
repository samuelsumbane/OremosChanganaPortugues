package com.samuel.oremoschanganapt.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoEvent
import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.InputPesquisa
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuel.oremoschanganapt.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanticosPage(state: CancaoState, navController: NavController, value: String, onEvent: (CancaoEvent) -> Unit){

    var pesquisaTexto by remember { mutableStateOf("") }
    var pesquisaTextoAvancada by remember { mutableStateOf("") }


    val dados = when(value){
        "todos" -> state.cancoes
        "new" -> state.cancoes.filter{ it.numero.startsWith("0") }
        else -> state.cancoes.filter{ it.grupo == value }
    }

//    Log.d("values", "${state.cancoes.size} / ${dados.size}")

//    var activeInput = 0 //normal
    var activeInput by remember { mutableStateOf(0) }

    LaunchedEffect(activeInput) {
        pesquisaTexto = ""
        pesquisaTextoAvancada = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Canticos", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth().weight(0.8f)
                        ) {
                            val labelText =
                                if (activeInput == 0) "Pesquisar título ou número (${dados.size} / ${state.cancoes.size})" else "Pesquisar no corpo (${dados.size} / ${state.cancoes.size})"
                            val inputValue =
                                if (activeInput == 0) pesquisaTexto else pesquisaTextoAvancada

                            InputPesquisa(
                                value = inputValue,
                                onValueChange = {
                                    if (activeInput == 0) pesquisaTexto =
                                        it else pesquisaTextoAvancada = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                    .height(58.dp),
                                label = labelText,
                                maxLines = 1,
                            )

                        }

                        Row(
                            modifier = Modifier
                                 .width(50.dp)
                                .padding(0.dp, 7.dp, 0.dp, 0.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    activeInput = if (activeInput == 0) { 1 } else { 0 }
                                },
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_reloadicon),
                                    contentDescription = "Trocar o campo de pesquisa",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(22.dp),
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBarPrincipal(navController, "canticosAgrupados")
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
//
                        val numOrNot = isNumber(pesquisaTexto)
                        if (numOrNot) {
                            dados.filter { it.numero == pesquisaTexto }
                        } else {
                            dados.filter { it.titulo.contains(pesquisaTexto, ignoreCase = true) }
                        }
                    }else if (pesquisaTextoAvancada.isNotBlank()){
                            val numOrNot = isNumber(pesquisaTextoAvancada)
                            if (numOrNot){
                                dados.filter { it.numero == pesquisaTextoAvancada }
                            }else{
                                dados.filter{ it.titulo.contains(pesquisaTextoAvancada, ignoreCase = true) ||
                                        it.corpo.contains(pesquisaTextoAvancada, ignoreCase = true)
                                }
                            }
                    } else { dados }

                ) { cancao ->
                    val n = cancao.numero
                    val t = cancao.titulo
//                    val sT = cancao.subTitulo ?: ""
                    val g = cancao.corpo
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .clickable {
                                navController.navigate("eachCantico/${n}/${t}/${g}")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.9f)
                                .fillMaxHeight()
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.1f)
                                    .height(60.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = cancao.numero,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = cancao.titulo,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = cancao.subTitulo,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        IconButton(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.1f)
                                .height(60.dp),
                            onClick = {
                                val status = if (cancao.favorito){
                                    false
                                }else{
                                    true
                                }
                                onEvent(CancaoEvent.UpdateFavorito(cancaoId = cancao.id, novoFavorito = status))
                            }
                        ){
                            if (cancao.favorito){
                                Icon(imageVector = Icons.Default.Star, contentDescription = "É favorito", tint = Orange)
                            }else{
                                Icon(imageVector = Icons.Outlined.Star, contentDescription = "Não é favorito", tint = White)
                            }
                        }
                    }
                }
            }
        }

        ShortcutsButton(navController)
    }
}


