package com.samuel.oremoschanganapt.view

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.functionsKotlin.ShareIconButton
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachCantico(navController: NavController, songId: Int, songViewModel: SongViewModel, defViewModel: DefViewModel){

    val allDef by defViewModel.defs.collectAsState()
    val songData = songViewModel.getSongById(songId)

    if(songData != null) {
        var lovedSong by remember { mutableStateOf(songData.loved) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text="Cântico: ${songData.number}", color = MaterialTheme.colorScheme.onPrimary) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    navigationIcon = {
                        IconButton(onClick={  navController.popBackStack()  } ){
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    actions = {
                        val context = LocalContext.current

                        lovedSong = StarButton(
                            itemLoved = lovedSong,
                            prayViewModel = null,
                            songViewModel = songViewModel,
                            id = songData.songId,
                            view = "songViewModel"
                        )

                        ShareIconButton(context,  text = "${songData.number} - ${songData.title} \n ${songData.body}")
                    }
                )
            },

            ){paddingValues ->

            val scrollState = rememberScrollState()

            if(allDef.isNotEmpty()) {

                val def = allDef.first()
                val dbScale by remember { mutableStateOf(def.textScale) }
                var scale by remember { mutableStateOf(dbScale.toFloat()) }


                Box(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = scale * zoom
                            scale = newScale.coerceIn(1.0f, 2.0f) // intervalo do zoom.
                            defViewModel.updateDef("textScale", scale.toDouble())
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = (songData.title).uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp * scale,
                            lineHeight = (24.sp * scale)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        //            Text(text = subTitulo, fontStyle = FontStyle.Italic)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = songData.body, fontSize = 19.sp * scale, lineHeight = (24.sp * scale))

                    }

                }
                ShortcutsButton(navController)

            }
        }
    } else {
        Text("Carregando a cantico...")
    }

}
