package com.samuel.oremoschanganapt.view

//import androidx.compose.material3.Text
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoEvent
import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.apresentacaoOracao.OracaoState
import com.samuel.oremoschanganapt.apresentacaoOracao.OracoesEvent
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.InputPesquisa
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosPage(state: OracaoState, cstate: CancaoState, navController: NavController, onEvent: (CancaoEvent) -> Unit, onEventO: (OracoesEvent) -> Unit){
    var pesquisaTexto by remember { mutableStateOf("") }

    val fCancoes = cstate.cancoes.filter{ it.favorito == true}
    val fOracoes = state.oracoes.filter{ it.favorito == true}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Canticos", color = MaterialTheme.colorScheme.tertiary)},
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
                        label = "Pesquisar favoritos",
                        maxLines = 1,
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBarPrincipal(navController, "favoritospage")
        }
    ){paddingVales ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingVales),
        ){
            if (fCancoes.size == 0 && fOracoes.size == 0){
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Nenhumo cântico encontrado.", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                }
            }else if (fCancoes.isNotEmpty()) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        if (pesquisaTexto.isNotBlank()) {
                            //
                            val numOrNot = isNumber(pesquisaTexto)
                            if (numOrNot) {
                                fCancoes.filter { it.numero == pesquisaTexto }
                            } else {
                                fCancoes.filter {
                                    it.titulo.contains(
                                        pesquisaTexto,
                                        ignoreCase = true
                                    )
                                }
                            }
                        } else {
                            fCancoes
                        }
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
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                .clickable {
                                    navController.navigate("eachCantico/${n}/${t}/${g} ")
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.9f)
                                    .fillMaxHeight()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.1f)
                                        .height(60.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
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

                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.1f)
                                    .height(60.dp),

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
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
                                    Icon(imageVector = Icons.Default.Star, contentDescription = "É favorito", tint = Orange)
                                }
                            }
                        }
                    }
                }
            }


        }
    }

}

