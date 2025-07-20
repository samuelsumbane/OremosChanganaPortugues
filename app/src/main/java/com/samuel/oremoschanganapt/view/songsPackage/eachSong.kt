package com.samuel.oremoschanganapt.view.songsPackage

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.SetIdPreference
import com.samuel.oremoschanganapt.components.StarButton
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.textFontSize
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.functionsKotlin.shareText
import com.samuel.oremoschanganapt.getIdSet
import com.samuel.oremoschanganapt.saveIdSet
import com.samuel.oremoschanganapt.view.states.AppState.configSongNumber
import com.samuelsumbane.oremoschanganapt.db.data.praysData
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EachSong(
    navController: NavController,
    songId: Int,
) {

    val songData = songsData.first { it.id == songId }
    var lovedSongsIds by remember { mutableStateOf(setOf<Int>())}
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var lovedState by remember { mutableStateOf(false) }
    lovedState = songId in lovedSongsIds
    configSongNumber = songData.number.toInt()

    LaunchedEffect(lovedSongsIds) {
        lovedSongsIds = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
    }

    var expanded by remember { mutableStateOf(false) }
    val menuBtns = listOf("Lembrete", "Partilhar")
    val btnsIcons = mapOf("Lembrete" to Icons.Default.Notifications, "Partilhar" to Icons.Default.Share)

    var reminderColor by remember { mutableStateOf(Color.Gray) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${stringResource(R.string.song)}: $configSongNumber", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("canticospage/todos/todos cânticos")  } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = stringResource(R.string.go_back))
                    }
                },
                actions = {
                    val context = LocalContext.current
                    // ---------->>
                    StarButton(lovedState) {
                        coroutineScope.launch {
                            if (lovedState) {
                                lovedSongsIds.toMutableSet().remove(songId)
                            } else {
                                lovedSongsIds.toMutableSet().add(songId)
                            }
                            saveIdSet(context, lovedSongsIds, SetIdPreference.SONGS_ID.preferenceName)
                            Log.d("songs", "to save $lovedSongsIds")
                            lovedState = !lovedState
                        }
                    }

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.options))
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            properties = PopupProperties(focusable = true),
                            modifier = Modifier.shadow(elevation = 3.dp, spotColor = Color.DarkGray)
                        ) {
                            menuBtns.forEach {
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = { Text(it) },
                                    trailingIcon = { Icon(btnsIcons[it]!!, contentDescription = "l", Modifier.size(18.dp)) },
                                    onClick = {
                                        when(it) {
                                            "Lembrete" -> {
//                                                    var hasReminder = false
//                                                    if (reminderes.isNotEmpty()){
//                                                        for(reminder in reminderes){
//                                                            if (reminder.reminderTable == "Pray"){
//                                                                if (reminder.reminderId == songId){
//                                                                    hasReminder = true
//                                                                }
//                                                            }
//                                                        }
//                                                    } else { hasReminder = false }
//
//                                                    if (hasReminder) {
//                                                        reminderViewModel.deleteReminder(songId)
//                                                        reminderColor = Orange
//                                                    } else {
//                                                        navController.navigate("configurereminder/$songId/Song/0/0")
//                                                        reminderColor = Color.Gray
//                                                    }
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
        }) { paddingValues ->
        val scrollState = rememberScrollState()
        val pagerState = rememberPagerState(
            initialPage = songId.toInt() - 1, pageCount = { songsData.size })

        /**
         * In pagerState, initialPage receives songId - 1 because, will be page + 1
         * in page inside HorizontalPager
         */

        HorizontalPager(
            state = pagerState, pageSpacing = 15.dp
        ) { page ->
            val dbScale by remember { mutableDoubleStateOf(1.3) }

            songsData.first { it.id == page + 1 }.run {
                configSongNumber = number.toInt()

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = title.uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = (textFontSize().value + 3).sp,
                            textAlign = TextAlign.Center,
                            softWrap = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = subTitle,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = body.trimIndent(),
                            fontSize = textFontSize(),
                            softWrap = true,
                            modifier = Modifier.padding(15.dp).fillMaxWidth()
                        )
                    }

                    ShortcutsButton(navController)
                }
            }
        }
    }
}
