package com.samuel.oremoschangana.view



//import androidx.compose.material.Text
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschangana.R
import com.samuel.oremoschangana.components.BottomAppBarPrincipal
import com.samuel.oremoschangana.ui.theme.HomeColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var scale by remember { mutableStateOf(1f) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)

                            .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale *= zoom
                            }

                        }
                    ){
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "Oremos Changana - Português",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Programador",
                                fontWeight = FontWeight.Bold,
                                fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black
                            )
                            Text(text = "Samuel Eugénio Sumbane", fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black)
                            Text(text = "Programador Full-Stack", fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black)
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Contacto",
                                fontWeight = FontWeight.Bold,
                                fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black
                            )

                            Text(text = "+258 865230661 / +258 833597867", fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black)
                            Text(text = "samuelsumbane143@gmail.com", fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black)
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Apoio",
                                fontWeight = FontWeight.Bold,
                                fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black
                            )
                            Text(
                                text = "A produção deste aplicativo carreceu de alguns custos da parte do programador, neste sentido pretende-se produzir mais aplicativos desta natureza e para tal contamos com seu apoio financeiro que pode ser efectuado através do numero 865230661.",
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(7.dp, 0.dp, 7.dp, 0.dp),
                                fontSize = (16.sp * scale), lineHeight = (24.sp * scale),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(text = "Versão: 2.1", color = Color.Gray, fontSize = (16.sp * scale), lineHeight = (24.sp * scale))
                        }
                    }
            }
        }
    ) {
        Scaffold( bottomBar = { BottomAppBarPrincipal(navController, "home") }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HomeColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cruz),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Row(){
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.3f)
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 40.dp),
                        ),
//                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Oremos",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 45.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "A HI KHONGELENI",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}
