package com.samuel.oremoschanganapt.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.repository.colorObject

@Composable
fun SearchContainer(
    pesquisatexto: String,
    searchInputLabel: String = "Pesquisar oração",
    isContainerActive: Boolean = false,
    showIcon: Boolean = true
): String{
    var activeContainer by remember { mutableStateOf(isContainerActive) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val percent = ( 35.0 * screenWidth.toDouble()  / 100)
    val percentTwoDigits = String.format("%.2f", percent).toDouble()
    val columnW = screenWidth - percentTwoDigits
    var pesquisaTexto by remember { mutableStateOf(pesquisatexto) }
//    val showIcon =
//    val iconColor = MaterialTheme.colorScheme.onPrimary

    Column(
        Modifier
            .width(if(activeContainer) columnW.dp else 40.dp)
            .height(64.dp)
//            .background(Color.Red)
            .clickable { activeContainer = !activeContainer }
    ){
        if(activeContainer){
            Row {
                InputPesquisa(
                    value = pesquisaTexto,
                    onValueChange = { pesquisaTexto = it },
                    modifier = Modifier.fillMaxWidth(0.9f).height(52.dp),
                    label = searchInputLabel,
                    maxLines = 1
                )

                if (showIcon) {
                    Column(Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ){
                        IconButton(onClick = {activeContainer = !activeContainer}){
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription="Close search input",
                                modifier = Modifier.width(30.dp).fillMaxHeight(0.9f)
                            )
                        }
                    }
                }

            }

        } else {
            Column( Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ){
                Spacer(Modifier.height(10.dp))
                Icon(Icons.Default.Search, contentDescription="Search",
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    }
    return pesquisaTexto
}
