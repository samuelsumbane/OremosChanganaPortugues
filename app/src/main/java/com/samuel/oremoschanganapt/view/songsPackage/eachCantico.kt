package com.samuel.oremoschanganapt.view.songsPackage

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.twotone.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.StarButton
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.shareText
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachCantico(navController: NavController, songId: Int,
                songViewModel: SongViewModel,
                reminderViewModel: ReminderViewModel,
                defViewModel: DefViewModel
) {

    val allDef by defViewModel.defs.collectAsState()
    val songData = songViewModel.getSongById(songId)

    if (songData != null) {
        val reminderes by reminderViewModel.reminders.collectAsState()
        var lastNavBtnClicked by remember { mutableStateOf("") }

        fun navigateToPrevious() {
            if (songData.number != "001") {
                val lastSongId = songData.songId - 1
                navController.navigate("eachCantico/${lastSongId}")
                lastNavBtnClicked = "Prev"
            }
        }

        fun navigateToNext() {
            if (songData.number.toInt() < 542) {
                val nextSongId = songData.songId + 1
                navController.navigate("eachCantico/${nextSongId}")
                lastNavBtnClicked = "Next"
            }
        }

        var expanded by remember { mutableStateOf(false) }
        val menuBtns = listOf("Lembrete", "Partilhar")
        val btnsIcons = mapOf("Lembrete" to Icons.Default.Notifications, "Partilhar" to Icons.Default.Share)

        var reminderColor by remember { mutableStateOf(Color.Gray) }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text="Cântico: ${songData.number}", color = MaterialTheme.colorScheme.tertiary) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    navigationIcon = {
                        IconButton(onClick={  navController.navigate("canticosAgrupados")  } ){
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    actions = {
                        val context = LocalContext.current
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            NavContentButton(lastNavBtnClicked, "Prev") {
                                navigateToPrevious()
                            }
                            NavContentButton(lastNavBtnClicked, "Next") {
                                navigateToNext()
                            }
                        }

                        // ---------->>
                        var lovedState by remember { mutableStateOf(songData.loved) }
                        StarButton(lovedState) {
                            if (lovedState) {
                                songViewModel.setLovedSong(songData.songId, false)
                            } else {
                                songViewModel.setLovedSong(songData.songId, true)
                            }
                            lovedState = !lovedState
                        }

                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Opcoes")
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                properties = PopupProperties(focusable = true)
                            ) {
                                menuBtns.forEach {
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = { Text(it) },
                                        trailingIcon = { Icon(btnsIcons[it]!!, contentDescription = "l", Modifier.size(18.dp)) },
                                        onClick = {
                                            when(it) {
                                                "Lembrete" -> {
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
                                                        reminderViewModel.deleteReminder(songId)
                                                        reminderColor = Orange
                                                    } else {
                                                        navController.navigate("configurereminder/$songId/Song/0/0/0")
                                                        reminderColor = Color.Gray
                                                    }
                                                }
                                                "Partilhar" -> {
                                                    shareText(context, "${songData.number} - ${songData.title} \n ${songData.body}")
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }


                    }
                )
            },

            ) { paddingValues ->

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

                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount > 100) navigateToPrevious()
                            else if (dragAmount < -100) navigateToNext()
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
                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = (songData.title).uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp * scale,
                            lineHeight = (24.sp * scale),
                            softWrap = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = songData.subTitle, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = songData.body.trimIndent(), fontSize = 19.sp * scale, lineHeight = (24.sp * scale), softWrap = true, modifier = Modifier
                            .fillMaxWidth().padding(15.dp)
                        )
                    }
                }
                ShortcutsButton(navController)
            }
        }
    } else LoadingScreen()
}

@Composable
fun NavContentButton(lastNavBtnClicked: String, to: String, onClick: () -> Unit) {
    val scale = remember { androidx.compose.animation.core.Animatable(1f) }

    LaunchedEffect(lastNavBtnClicked) {
        scale.animateTo(
            targetValue = 1.7f, // Increase to 1.5x ------>>
            animationSpec = tween(durationMillis = 200)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 200)
        )
    }

    IconButton(onClick = onClick) {
        val scalePrevValue = if (lastNavBtnClicked == "Prev") scale.value else 1f
        val scaleNextValue = if (lastNavBtnClicked == "Next") scale.value else 1f
        when (to) {
            "Prev" -> Icon(Icons.TwoTone.KeyboardArrowLeft, contentDescription = "Back", modifier = Modifier.size(30.dp).graphicsLayer(
                scaleX = scalePrevValue,
                scaleY = scalePrevValue
            ))
            "Next" -> Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next", modifier = Modifier.size(30.dp).graphicsLayer(
                scaleX = scaleNextValue,
                scaleY = scaleNextValue
            ))
        }
    }
}