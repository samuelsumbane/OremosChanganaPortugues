package com.samuel.oremoschanganapt.view.songsPackage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
//import com.samuel.oremoschanganapt.apresentacaoOracao.CancaoState
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

var grupos = listOf("novos cânticos ", "todos cânticos", "entrada / a ku sungula a ntirho", "acto penitencial", "glória", "aclamação ao envagelho / a ku twalisa envagelho", "ofertório / ta minyikelo", "elevação", "pai-nosso / ta bava wa hina", "saudacao da paz / ta ku losava hi ku rula", "cordeiro de deus / xinhempfana", "comunhão / ta xilalelo", "acção de graças / ta ku tlangela", "natal / ta ku pswaliwa ka ", "quaresma / ta nkari wa mahlomulo", "páscoa / ta nkarhi wa paskwa", "ascensão e pentecconstes / ta ku xika ka moya wa ku kwetsima", "nossa senhora / ta maria wa ku phat", "baptismo - profissão de fé/ ta ntsakamiso", "catecumenado, vocação, apostolado", "matrimónio", "adoração, bênção, acção de grança", "funerais / ta makhombo", "uso vário / tinsimu tinwani" )

var gruposvalores = listOf("new", "todos", "Entrada", "ActoPenitencial", "Gloria", "Aclamacao", "Ofertorio", "Elevacao", "PaiNosso", "SaudacaoPaz", "CordeiroDeus", "Comunhao", "Gracas", "Natal", "Quaresma", "Pascoa", "Ascensao", "NossaSenhora", "Baptismo", "Catecumenado", "Matrimonio", "Adoracao", "Funerais", "UsoVario", "Gracas")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanticosAgrupados( navController: NavController, songViewModel: SongViewModel ){

    val allSongs by songViewModel.songs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Cânticos Agrupados", color = MaterialTheme.colorScheme.onPrimary, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={navController.popBackStack()}
                    ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBarPrincipal(navController, "canticosAgrupados")
        }

        ) { paddingVales ->

        val mainColor = colorObject.mainColor

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingVales),
        ) {

            if(allSongs.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(1){
                        grupos.forEachIndexed { index, g ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(65.dp)
                                    .padding(5.dp)
                                    .background( mainColor,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable {
                                        if (gruposvalores[index] != "indice") {
                                            navController.navigate("canticospage/${gruposvalores[index]}/${grupos[index]}")
                                        } else {
                                            navController.navigate("conticospage/outro")
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(text = (g).uppercase(), textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            } else {
                LoadingScreen("Cânticos agrupados")
            }
        }
        ShortcutsButton(navController)

    }
}

