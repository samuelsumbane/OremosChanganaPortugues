package com.samuel.oremoschanganapt.view.praysPackage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ArrowBack
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
import com.samuel.oremoschanganapt.functionsKotlin.ShareIconButton
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.PopupProperties
import com.samuel.oremoschanganapt.components.StarButton
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
//import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuel.oremoschanganapt.functionsKotlin.shareText
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachOracao(navController: NavController, prayId: Int,
               prayViewModel: PrayViewModel,
               defViewModel: DefViewModel,
               reminderViewModel: ReminderViewModel,
) {

    val allDef by defViewModel.defs.collectAsState()
    val prayData = prayViewModel.getPrayById(prayId)
    val menuBtns = listOf("Lembrete", "Partilhar")
//    val btnsIcons = mapOf("Lembrete" to Icons.Default.Notifications, "Partilhar" to Icons.Default.Share)
    val btnsIcons = mutableMapOf("Lembrete" to Icons.Default.Notifications, "Partilhar" to Icons.Default.Share)

    var expanded by remember { mutableStateOf(false) }

    if (prayData != null){
        val reminderes by reminderViewModel.reminders.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text="Oração") },
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
                        // The star icon ------>>
                        var lovedState by remember { mutableStateOf(prayData.loved) }
                        StarButton(lovedState) {
                            if (lovedState) {
                                prayViewModel.setLovedPray(prayData.prayId, false)
                            } else {
                                prayViewModel.setLovedPray(prayData.prayId, true)
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
                                                                if (reminder.reminderId == prayId){
                                                                    hasReminder = true
                                                                }
                                                            }
                                                        }
                                                    } else { hasReminder = false }


                                                    if (hasReminder) {
                                                        // by now the fun is delete reminder ----->>
                                                         reminderViewModel.deleteReminder(prayId)
    //                                                   Icon(Icons.Default.CheckCircle, contentDescription = "reminder set")
                                                    } else {
                                                            // by now fun is add reminder ------>>
                                                        navController.navigate("configurereminder/$prayId/Pray/0/0")
                                                    }
                                                }
                                                "Partilhar" -> {
                                                    shareText(context, "${prayData.title} \n ${prayData.body}")
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

            if(allDef.isNotEmpty()){

                val def = allDef.first()
                val dbScale by  remember { mutableDoubleStateOf(def.textScale) }
                var scale by remember { mutableFloatStateOf(dbScale.toFloat()) }

                Box(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = scale * zoom
                        scale = newScale.coerceIn(1.0f, 2.0f) // intervalo do zoom.
                        defViewModel.updateDef("textScale", scale.toDouble())
                    }
                }) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(paddingValues)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = prayData.title.uppercase(), fontWeight = FontWeight.Bold, fontSize = 17.sp * scale, lineHeight = (24.sp * scale))
                        Spacer(modifier = Modifier.height(12.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = prayData.body.trim(),
                            modifier = Modifier.fillMaxWidth().padding(14.dp, 0.dp, 14.dp, 0.dp),
                            fontSize = 19.sp * scale, lineHeight = (24.sp * scale)
                        )
                    }
                }

                ShortcutsButton(navController, )
            }
        }

    } else {
        Text("Carregando oracao...")
    }

}
