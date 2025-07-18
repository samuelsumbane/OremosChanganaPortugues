package com.samuel.oremoschanganapt.view.praysPackage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.PopupProperties
import com.samuel.oremoschanganapt.components.StarButton
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
//import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.getIdSet
import com.samuel.oremoschanganapt.saveIdSet
//import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.data.praysData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EachPray(
    navController: NavController,
    prayId: Int,
) {

//    val allDef by defViewModel.defs.collectAsState()
    val prayData = praysData.first { it.id == prayId }
    val menuBtns = listOf("Lembrete", "Partilhar")
    val btnsIcons = mutableMapOf("Lembrete" to Icons.Default.Notifications, "Partilhar" to Icons.Default.Share)

    var expanded by remember { mutableStateOf(false) }
    var lovedPrayIds by remember { mutableStateOf(setOf<Int>()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

//    val reminderes by reminderViewModel.reminders.collectAsState()
    LaunchedEffect(lovedPrayIds) {
        lovedPrayIds = getIdSet(context, "songs_id_set")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Oração") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick={  navController.popBackStack()  }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    val context = LocalContext.current
                    // The star icon ------>>
                    var lovedState by remember { mutableStateOf(prayId in lovedPrayIds) }
                    StarButton(lovedState) {
                        coroutineScope.launch {
                            if (lovedState) {
                                lovedPrayIds.toMutableSet().remove(prayId)
                            } else {
                                lovedPrayIds.toMutableSet().add(prayId)
                            }
                            saveIdSet(context, lovedPrayIds, "prays_id_set")
                            lovedState = !lovedState
                        }
                    }

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Opcoes")
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            properties = PopupProperties(focusable = true),
                            modifier = Modifier.shadow(elevation = 3.dp, spotColor = Color.DarkGray)

                        ) {
                            menuBtns.forEach {
//                                DropdownMenuItem(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    text = { Text(it) },
//                                    trailingIcon = { Icon(btnsIcons[it]!!, contentDescription = "l", Modifier.size(18.dp)) },
//                                    onClick = {
//                                        when(it) {
//                                            "Lembrete" -> {
//                                                var hasReminder = false
//                                                if (reminderes.isNotEmpty()){
//                                                    for(reminder in reminderes){
//                                                        if (reminder.reminderTable == "Pray"){
//                                                            if (reminder.reminderId == prayId){
//                                                                hasReminder = true
//                                                            }
//                                                        }
//                                                    }
//                                                } else { hasReminder = false }
//
//
//                                                if (hasReminder) {
//                                                    // by now the fun is delete reminder ----->>
//                                                     reminderViewModel.deleteReminder(prayId)
////                                                   Icon(Icons.Default.CheckCircle, contentDescription = "reminder set")
//                                                } else {
//                                                        // by now fun is add reminder ------>>
//                                                    navController.navigate("configurereminder/$prayId/Pray/0/0")
//                                                }
//                                            }
//                                            "Partilhar" -> {
//                                                shareText(context, "${prayData.title} \n ${prayData.body}")
//                                            }
//                                        }
//                                    }
//                                )
                            }
                        }
                    }
                }
            )
        },

        ) { paddingValues ->
            val scrollState = rememberScrollState()
            val pagerState = rememberPagerState(pageCount = { praysData.size })

            HorizontalPager(
                state = pagerState,
                pageSpacing = 15.dp
            ) { page ->
                val dbScale by  remember { mutableDoubleStateOf(1.3) }
                var scale by remember { mutableFloatStateOf(dbScale.toFloat()) }

                praysData.first { it.id == page + 1 }.run {
//                    Box(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
//                        detectTransformGestures { _, pan, zoom, _ ->
//                            val newScale = scale * zoom
//                            scale = newScale.coerceIn(1.0f, 2.0f) // intervalo do zoom.
//                            //                    defViewModel.updateDef("textScale", scale.toDouble())
//                        }
//                    }) {
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ShortcutsButton(navController)

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = title.uppercase(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp * scale,
                                lineHeight = (24.sp * scale)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = body.trim(),
                                modifier = Modifier
                                    .padding(14.dp, 0.dp, 14.dp, 0.dp)
                                    .fillMaxWidth(),
                                fontSize = 19.sp * scale,
                                lineHeight = (24.sp * scale)
                            )

                        }
//                    }
                }

        }

    }


}
