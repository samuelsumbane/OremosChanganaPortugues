package com.samuel.oremoschanganapt.view

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.buttons.ShortcutButtonChild
import kotlinx.coroutines.coroutineScope
import kotlin.math.roundToInt

@Composable
fun ShortcutsButton(navController: NavController){

    var isActive by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toDouble()
    val screenPercent = 10 * screenHeight / 100

    Box(Modifier.fillMaxSize().background(Color.Transparent) ){

        var offsetY by remember { mutableStateOf(screenHeight) }
        var childColumnHeight by remember { mutableStateOf(200) }

        LaunchedEffect(offsetY) {
            coroutineScope {
                if(offsetY < screenPercent){
                    offsetY = screenPercent - 10
                } else if ( offsetY > screenHeight * 1.20){
                    offsetY = screenHeight * 1.17
                }
            }
        }

        Row(
            Modifier.align(Alignment.TopEnd)
//                .background(Color.Yellow)
                .fillMaxHeight()
                .offset {IntOffset(0, offsetY.roundToInt())}
        ){

        if(isActive){
            Column(
                Modifier.width(70.dp)
                    .height(childColumnHeight.dp)
                ,
                verticalArrangement = Arrangement.Center
            ){
                // canticos
                ShortcutButtonChild(
                    modifier = Modifier.align(Alignment.End),
                    icon = ImageVector.vectorResource(id = R.drawable.ic_music),
                    description = "Canticos",
                    iconModifier = Modifier.size(26.dp)
                ){  navController.navigate("canticosAgrupados") }

                Spacer(Modifier.height(10.dp))

                // oracoes
                ShortcutButtonChild(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_pray),
                    description = "Oracoes",
                    iconModifier = Modifier.size(26.dp)
                ){ navController.navigate("oracoespage") }

                Spacer(Modifier.height(10.dp))

                ShortcutButtonChild(
                    modifier = Modifier.align(Alignment.End),
                    icon = Icons.Outlined.Star,
                    description = "Favoritos"
                ) {  navController.navigate("favoritospage") }

            }
        }

            Column(
                Modifier.width(50.dp)
//                    .background(Color.Red)
                    .height(childColumnHeight.dp),
                verticalArrangement = Arrangement.Center
            ){
                IconButton(
                    modifier = Modifier
                        .size(50.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                offsetY += dragAmount.y
                            }
                        },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    onClick = { isActive = !isActive }
                ) {
//                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.brightness), contentDescription = "s", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}