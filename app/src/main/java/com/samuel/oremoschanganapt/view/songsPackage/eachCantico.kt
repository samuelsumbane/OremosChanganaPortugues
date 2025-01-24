package com.samuel.oremoschanganapt.view.songsPackage

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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
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
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.ShareIconButton
import com.samuelsumbane.oremoschanganapt.db.CommonViewModel
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachCantico(navController: NavController, songId: Int,
                songViewModel: SongViewModel,
                reminderViewModel: ReminderViewModel,
                defViewModel: DefViewModel, commonViewModel: CommonViewModel
){

    val allDef by defViewModel.defs.collectAsState()
    val songData = songViewModel.getSongById(songId)


    if(songData != null) {
        val reminderes by reminderViewModel.reminders.collectAsState()
        //
        val songLoved = commonViewModel.getLovedItem("Song", songData.songId)
//        val itemIsLoved: Boolean = songLoved != null
        val lovedState = remember { mutableStateOf(songLoved != null) }
        
//        var lovedSong by remember { mutableStateOf() }
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

                        var hasReminder = false
                        if (reminderes.isNotEmpty()){
                            for(reminder in reminderes){
                                if (reminder.reminderTable == "Pray"){
                                    if (reminder.reminderId == songId){
                                        hasReminder = true
                                    }
                                }
                            }
                        } else { hasReminder = false }

                        if (hasReminder) {
                            IconButton(
                                // by now the fun is delete reminder ----->>
                                onClick = {
                                    reminderViewModel.deleteReminder(songId)
                                }
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "reminder set")
                            }
                        } else {
                            IconButton(
                                // by now fun is add reminder ------>>
                                onClick = {
                                    navController.navigate("configurereminder/$songId/Song/0/0/0")
                                }
                            ) {
                                Icon(Icons.Default.Call, contentDescription = "no reminder set")
                            }
                        }

                        // ---------->>
                        StarButton(lovedState = lovedState) {
                            if (lovedState.value) {
                                commonViewModel.removeLovedId("Song", songData.songId)
                            } else {
                                commonViewModel.addLovedId("Song", songData.songId)
                            }
                        }

                        ShareIconButton(context,  text = "${songData.number} - ${songData.title} \n ${songData.body}")
                    }
                )
            },

            ) { paddingValues ->

            val scrollState = rememberScrollState()
            if(allDef.isNotEmpty()) {
                val def = allDef.first()
                val dbScale by remember { mutableStateOf(def.textScale) }
                var scale by remember { mutableStateOf(dbScale.toFloat()) }

                Box(modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = scale * zoom
                            scale = newScale.coerceIn(1.0f, 2.0f) // intervalo do zoom.
                            defViewModel.updateDef("textScale", scale.toDouble())
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
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
    } else LoadingScreen()

}
